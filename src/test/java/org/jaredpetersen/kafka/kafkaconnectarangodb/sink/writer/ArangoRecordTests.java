package org.jaredpetersen.kafka.kafkaconnectarangodb.sink.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ArangoRecordTests {
  @Test
  void getCollectionReturnsCollection() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertEquals("somecollection", record.getCollection());
  }

  @Test
  void getKeyReturnsKey() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertEquals("somekey", record.getKey());
  }

  @Test
  void getValueReturnsValue() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertEquals("somevalue", record.getValue());
  }

  @Test
  void equalsNullReturnsFalse() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertFalse(record.equals(null));
  }

  @Test
  void equalsSameObjectReturnsTrue() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertTrue(record.equals(record));
  }

  @Test
  void equalsDifferentObjectTypeReturnsFalse() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertFalse(record.equals("something else"));
  }

  @Test
  void equalsEqualObjectReturnsTrue() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertTrue(recordA.equals(recordB));
  }

  @Test
  void equalsDifferentCollectionReturnsFalse() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("DIFFERENT", "somekey", "somevalue");

    assertFalse(recordA.equals(recordB));
  }

  @Test
  void equalsDifferentKeyReturnsFalse() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "DIFFERENT", "somevalue");

    assertFalse(recordA.equals(recordB));
  }

  @Test
  void equalsDifferentValueReturnsFalse() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "somekey", "DIFFERENT");

    assertFalse(recordA.equals(recordB));
  }

  @Test
  void equalsDifferentValuesReturnsFalse() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("DIFFERENT", "DIFFERENT", "DIFFERENT");

    assertFalse(recordA.equals(recordB));
  }

  @Test
  void hashCodeSameValuesReturnsSame() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "somekey", "somevalue");

    assertEquals(recordA.hashCode(), recordB.hashCode());
  }

  @Test
  void hashCodeDifferentCollectionReturnsDifferent() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("DIFFERENT", "somekey", "somevalue");

    assertNotEquals(recordA.hashCode(), recordB.hashCode());
  }

  @Test
  void hashCodeDifferentKeyReturnsDifferent() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "DIFFERENT", "somevalue");

    assertNotEquals(recordA.hashCode(), recordB.hashCode());
  }

  @Test
  void hashCodeDifferentValueReturnsDifferent() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("somecollection", "somekey", "DIFFERENT");

    assertNotEquals(recordA.hashCode(), recordB.hashCode());
  }

  @Test
  void hashCodeDifferentValuesReturnsDifferent() {
    final ArangoRecord recordA = new ArangoRecord("somecollection", "somekey", "somevalue");
    final ArangoRecord recordB = new ArangoRecord("DIFFERENT", "DIFFERENT", "DIFFERENT");

    assertNotEquals(recordA.hashCode(), recordB.hashCode());
  }

  @Test
  void toStringReturnsStringified() {
    final ArangoRecord record = new ArangoRecord("somecollection", "somekey", "somevalue");
    final String expectedStringifiedRecords = "ArangoRecord{collection=somecollection, key=somekey, value=somevalue}";

    assertEquals(expectedStringifiedRecords, record.toString());
  }
}
