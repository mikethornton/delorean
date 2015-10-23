/**
 * 
 */
package com.css.delorean.command;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.css.delorean.mocks.MockTemporalDatastore;

/**
 * Tests that LatestCommand functions as expected.
 * 
 * @author dell
 *
 */
public class LatestCommandTest {
	private static final String ID = "id";
	private static final String RESPONSE = "response";
	private static final Object[] DATAS = { Long.valueOf(12l), RESPONSE };
	private LatestCommand command;

	@Before
	public void setup() {
		command = new LatestCommand(ID);
	}

	@Test
	public void latest() {
		assertNotNull("Should have returned a result", command.execute(new MockTemporalDatastore() {

			@Override
			public Object[] getLatest(Object key) {
				return DATAS;
			}

		}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNotFound() {
		command.execute(new MockTemporalDatastore() {

			@Override
			public Object[] getLatest(Object key) {
				return null;
			}

		});
	}
}
