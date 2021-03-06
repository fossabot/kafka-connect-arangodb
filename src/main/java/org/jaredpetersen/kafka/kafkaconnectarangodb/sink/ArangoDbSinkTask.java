package org.jaredpetersen.kafka.kafkaconnectarangodb.sink;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.json.JsonConverterConfig;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.jaredpetersen.kafka.kafkaconnectarangodb.sink.config.ArangoDbSinkConfig;
import org.jaredpetersen.kafka.kafkaconnectarangodb.sink.writer.ArangoRecord;
import org.jaredpetersen.kafka.kafkaconnectarangodb.sink.writer.RecordConverter;
import org.jaredpetersen.kafka.kafkaconnectarangodb.sink.writer.Writer;
import org.jaredpetersen.kafka.kafkaconnectarangodb.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kafka Connect Task for Kafka Connect ArangoDb Sink.
 */
public class ArangoDbSinkTask extends SinkTask {
  private static final Logger LOGGER = LoggerFactory.getLogger(ArangoDbSinkTask.class);

  private RecordConverter recordConverter;
  private Writer writer;

  @Override
  public final String version() {
    return PropertiesLoader.load().getProperty("version");
  }

  @Override
  public final void start(final Map<String, String> props) {
    LOGGER.info("task config: {}", props);

    // Set up database
    final ArangoDbSinkConfig config = new ArangoDbSinkConfig(props);
    final ArangoDB arangodb = new ArangoDB.Builder()
        .host(config.arangoDbHost, config.arangoDbPort)
        .user(config.arangoDbUser)
        .password(config.arangoDbPassword.value())
        .build();
    final ArangoDatabase database = arangodb.db(config.arangoDbDatabaseName);

    // Set up the record converter
    final JsonConverter jsonConverter = new JsonConverter();
    jsonConverter.configure(
        Collections.singletonMap(JsonConverterConfig.SCHEMAS_ENABLE_CONFIG, "false"),
        false);

    final JsonDeserializer jsonDeserializer = new JsonDeserializer();
    final ObjectMapper objectMapper = new ObjectMapper();

    this.recordConverter = new RecordConverter(jsonConverter, jsonDeserializer, objectMapper);

    // Set up the writer
    this.writer = new Writer(database);
  }

  @Override
  public final void put(final Collection<SinkRecord> records) {
    if (records.isEmpty()) {
      return;
    }

    LOGGER.info("writing {} record(s)", records.size());

    // Convert sink records into something that can be written
    final Collection<ArangoRecord> arangoRecords = new ArrayList<>();

    for (final SinkRecord sinkRecord : records) {
      final ArangoRecord arangoRecord = this.recordConverter.convert(sinkRecord);
      arangoRecords.add(arangoRecord);
    }

    // Write the ArangoDB records to the database
    this.writer.write(arangoRecords);
  }

  @Override
  public final void stop() {
    // Do nothing
  }
}
