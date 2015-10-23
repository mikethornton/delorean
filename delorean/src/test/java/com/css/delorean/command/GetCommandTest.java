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
 * @author dell
 *
 */
public class GetCommandTest {
	private static final String ID = "id";
	private static final Long TIMESTAMP = Long.valueOf(12);
	private static final String RESPONSE = "response";
	private static final String OK = ConversionService.OK_PREFIX + RESPONSE;
	private GetCommand command;

	@Before
	public void setup() {
		command = new GetCommand(ID, TIMESTAMP);
	}

	@Test
	public void get() {
		assertEquals("Should have returned a result", OK, command.execute(new MockTemporalDatastore() {

			@Override
			public String get(Object key, long timestamp) {
				return RESPONSE;
			}

		}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNotFound() {
		command.execute(new MockTemporalDatastore() {

			@Override
			public String get(Object key, long timestamp) {
				return null;
			}

		});
	}
}
