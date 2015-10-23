/**
 * 
 */
package com.css.delorean.data;

import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test that InMemoryTemporalDatastore functions as expected.
 * 
 * @author dell
 *
 */
public class InMemoryTemporalDatastoreTest {
	public static final String ID1 = "ID1";
	public static final String ID2 = "ID2";
	public static final long TIMESTAMP1 = 5;
	public static final long TIMESTAMP2 = 10;
	public static final long TIMESTAMP3 = 15;
	public static final long TIMESTAMP4 = 20;
	public static final String DATA_1 = "data1";
	public static final String DATA_2 = "data2";
	public static final String DATA_3 = "data3";
	public static final String DATA_4 = "data4";
	private InMemoryTemporalDatastore datastore;

	@Before
	public void setup() {
		datastore = new InMemoryTemporalDatastore();
		datastore.create(ID1, TIMESTAMP4, DATA_4);
		datastore.update(ID1, TIMESTAMP1, DATA_1);
		datastore.update(ID1, TIMESTAMP3, DATA_3);
		datastore.update(ID1, TIMESTAMP2, DATA_2);
	}

	@Test
	public void testCreate() {
		assertTrue("Should have created new history for ID2", datastore.create(ID2, TIMESTAMP1, DATA_1));
	}

	@Test
	public void testCantCreateTwice() {
		datastore.create(ID2, TIMESTAMP1, DATA_1);
		assertFalse("Shouldn't have created another history for ID2", datastore.create(ID2, TIMESTAMP2, DATA_2));
	}

	@Test
	public void testUpdateAsCreate() {
		assertNull("Should not have returned anything", datastore.update(ID2, TIMESTAMP1, DATA_1));
	}

	@Test
	public void testUpdateMiddle() {
		assertEquals("Returned the incorrect value", DATA_2, datastore.update(ID1, TIMESTAMP2 + 1, "newData"));
		assertEquals("Did not update correctly", "newData", datastore.get(ID1, TIMESTAMP2 + 2));
	}

	@Test
	public void testUpdateFloor() {
		assertNull("Should not have returned value", datastore.update(ID1, TIMESTAMP1 - 2, "newData"));
		assertEquals("Did not update correctly", "newData", datastore.get(ID1, TIMESTAMP1 - 1));
	}

	@Test
	public void testUpdateTop() {
		assertEquals("Returned the incorrect value", DATA_4, datastore.update(ID1, TIMESTAMP4 + 1, "newData"));
		assertEquals("Did not update correctly", "newData", datastore.get(ID1, TIMESTAMP4 + 2));
	}

	@Test
	public void testUpdateSameValue() {
		assertEquals("Returned the incorrect value", DATA_2, datastore.update(ID1, TIMESTAMP2, "newData"));
		assertEquals("Did not update correctly", "newData", datastore.get(ID1, TIMESTAMP2));
	}

	@Test
	public void testDelete() {
		assertEquals("Incorrect returned value", DATA_4, datastore.delete(ID1));
		assertNull("Should not return value as history has been deleted", datastore.get(ID1, TIMESTAMP2));
	}

	@Test
	public void testDeleteNoEntry() {
		assertNull("Should not have returned value", datastore.delete(ID2));
	}

	@Test
	public void testDeleteWithTimestampSame() {
		assertEquals("Incorrect returned value", DATA_1, datastore.delete(ID1, TIMESTAMP2));
		assertEquals("Should return value", DATA_1, datastore.get(ID1, TIMESTAMP4 + 1));
	}

	@Test
	public void testDeleteWithTimestampBeforeAll() {
		assertNull("Should not have returned value", datastore.delete(ID1, TIMESTAMP1 - 1));
		assertNull("Should have deleted all values", datastore.get(ID1, TIMESTAMP4 + 1));
	}

	@Test
	public void testDeleteWithTimestampNoEntry() {
		assertNull("Should not have returned value", datastore.delete(ID2, TIMESTAMP2 + 2));
	}

	@Test
	public void testDeleteWithTimestampNonMatching() {
		assertEquals("Incorrect returned value", DATA_2, datastore.delete(ID1, TIMESTAMP2 + 2));
		assertEquals("Should return value", DATA_2, datastore.get(ID1, TIMESTAMP4 + 1));
	}

	@Test
	public void testDeleteWithTimestampAfter() {
		assertEquals("Incorrect returned value", DATA_4, datastore.delete(ID1, TIMESTAMP4 + 2));
		assertEquals("Should return value", DATA_4, datastore.get(ID1, TIMESTAMP4 + 1));
	}

	@Test
	public void testGetBefore() {
		assertNull("Should not have returned value", datastore.get(ID1, TIMESTAMP1 - 1));
	}

	@Test
	public void testGetSame() {
		assertEquals("Incorrect returned value", DATA_2, datastore.get(ID1, TIMESTAMP2));
	}

	@Test
	public void testGetBetween() {
		assertEquals("Incorrect returned value", DATA_3, datastore.get(ID1, TIMESTAMP3 + 1));
	}

	@Test
	public void testGetAfter() {
		assertEquals("Incorrect returned value", DATA_4, datastore.get(ID1, TIMESTAMP4 + 1));
	}

	@Test
	public void testGetNotExists() {
		assertNull("Incorrect returned value", datastore.get(ID2, TIMESTAMP4 + 1));
	}

	@Test
	public void testGetLatest() {
		Object[] result = datastore.getLatest(ID1);
		assertNotNull("Should have returned result", result);
		assertEquals("Incorrect returned value", TIMESTAMP4, result[0]);
		assertEquals("Incorrect returned value", DATA_4, result[1]);
	}

	@Test
	public void testGetLatestNotExists() {
		assertNull("Should not have returned value", datastore.getLatest(ID2));
	}
}
