/**
 * 
 */
package com.css.delorean.command;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.css.delorean.mocks.MockTemporalDatastore;

/**
 * @author dell
 *
 */
public class CreateCommandTest {
	private static final String ID = "id";
	private static final Long TIMESTAMP = Long.valueOf(12);
	private static final String DATA = "data";
	private CreateCommand command;

	@Before
	public void setup() {
		command = new CreateCommand(ID, TIMESTAMP, DATA);
	}

	@Test
	public void create() {
		assertNotNull("Should have returned a result", command.execute(new MockTemporalDatastore() {

			@Override
			public boolean create(Object key, long timestamp, String data) {
				return true;
			}

		}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void notCreated() {
		command.execute(new MockTemporalDatastore() {

			@Override
			public boolean create(Object key, long timestamp, String data) {
				return false;
			}

		});
	}
}
