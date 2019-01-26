package org.jaredpetersen.kafka.kafkaconnectarangodb.sink;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.connect.sink.SinkTask;

import org.junit.jupiter.api.Test;

class ArangoDbSinkTaskTests {
  @Test
  void versionReturnsVersion() {
    final SinkTask task = new ArangoDbSinkTask();
    assertEquals("1.0.0", task.version());
  }

  @Test
  void stopDoesNothing() {
    final SinkTask task = new ArangoDbSinkTask();
    task.stop();
  }
}
