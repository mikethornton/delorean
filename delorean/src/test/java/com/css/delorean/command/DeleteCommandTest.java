/**
 * 
 */
package com.css.delorean.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.css.delorean.mocks.MockTemporalDatastore;
import com.css.delorean.service.ConversionService;

/**
 * Test that DeleteCommand functions as expected.
 * 
 * @author dell
 *
 */
public class DeleteCommandTest {
	private static final String ID = "id";
	private static final Long TIMESTAMP = Long.valueOf(12);
	private static final String RESPONSE = "response";
	private static final String OK = ConversionService.OK_PREFIX + RESPONSE;
	private DeleteCommand commandWithoutTimestamp;
	private DeleteCommand commandWithTimestamp;

	@Before
	public void setup() {
		commandWithoutTimestamp = new DeleteCommand(ID, null);
		commandWithTimestamp = new DeleteCommand(ID, TIMESTAMP);
	}

	@Test
	public void deleteWithIdOnly() {
		assertEquals("Incorrect response", OK, commandWithoutTimestamp.execute(new MockTemporalDatastore() {

			@Override
			public String delete(Object key) {
				return RESPONSE;
			}

		}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteWithIdOnlyNoResult() {
		commandWithoutTimestamp.execute(new MockTemporalDatastore() {

			@Override
			public String delete(Object key) {
				return null;
			}

		});

	}

	@Test
	public void deleteWithIdAndTimestamp() {
		assertEquals("Incorrect response", OK, commandWithTimestamp.execute(new MockTemporalDatastore() {

			@Override
			public String delete(Object key, long timestamp) {
				return RESPONSE;
			}

		}));

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteWithIdAndTimestampNoResult() {
		commandWithTimestamp.execute(new MockTemporalDatastore() {

			@Override
			public String delete(Object key, long timestamp) {
				return null;
			}

		});
	}
}
