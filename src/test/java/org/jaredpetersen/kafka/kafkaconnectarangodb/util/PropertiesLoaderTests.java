package org.jaredpetersen.kafka.kafkaconnectarangodb.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTests {
  @Test
  void constructorDoesNothing() {
    new PropertiesLoader();
  }

  @Test
  void loadLoadsProperties() {
    final Properties properties = PropertiesLoader.load();

    assertEquals("1.0.0", properties.get("version"));
  }
}
