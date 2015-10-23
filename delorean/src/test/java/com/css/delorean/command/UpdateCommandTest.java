/**
 * 
 */
package com.css.delorean.command;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.css.delorean.mocks.MockTemporalDatastore;

/**
 * Tests UpdateCommand functions as expected.
 * 
 * @author dell
 *
 */
public class UpdateCommandTest {
	private static final String ID = "id";
	private static final String RESPONSE = "response";
	private static final Long TIMESTAMP = Long.valueOf(12);
	private static final String DATA = "data";
	private UpdateCommand command;

	@Before
	public void setup() {
		command = new UpdateCommand(ID, TIMESTAMP, DATA);
	}

	@Test
	public void create() {
		assertNotNull("Should have returned a result", command.execute(new MockTemporalDatastore() {

			@Override
			public String update(Object key, long timestamp, String data) {
				return RESPONSE;
			}

		}));
	}

	@Test
	public void notCreated() {
		assertNotNull("Should have returned a result", command.execute(new MockTemporalDatastore() {

			@Override
			public String update(Object key, long timestamp, String data) {
				return null;
			}

		}));
	}
}
