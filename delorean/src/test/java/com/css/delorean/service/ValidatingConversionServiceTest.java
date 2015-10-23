/**
 * 
 */
package com.css.delorean.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.css.delorean.command.CreateCommand;
import com.css.delorean.command.DeleteCommand;
import com.css.delorean.command.GetCommand;
import com.css.delorean.command.LatestCommand;
import com.css.delorean.command.UpdateCommand;

/**
 * @author dell
 *
 */
public class ValidatingConversionServiceTest {
	public static final String CREATE = "CREATE";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String GET = "GET";
	public static final String LATEST = "LATEST";
	public static final String QUIT = "QUIT";
	public static final String ID_STR = "12";
	public static final Integer ID = Integer.valueOf("12");
	public static final String TIMESTAMP_STR = "345";
	public static final Long TIMESTAMP = Long.valueOf(TIMESTAMP_STR);
	public static final String DATA = "data";
	private ValidatingConversionService service;

	@Before
	public void setup() {
		service = new ValidatingConversionService();
	}

	@Test
	public void create() {
		CreateCommand command = (CreateCommand) service
				.convert(CREATE + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertEquals("Should have set timestamp", TIMESTAMP, command.getTimestamp());
		assertEquals("Should have set data", DATA, command.getData());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTooManyArgs() {
		service.convert(CREATE + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA + " fdasfdas");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTooFewArgs() {
		service.convert(CREATE + " " + ID_STR + " " + TIMESTAMP_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createInvalidId() {
		service.convert(CREATE + " " + DATA + " " + TIMESTAMP_STR + " " + DATA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createInvalidTimestamp() {
		service.convert(CREATE + " " + ID_STR + " " + DATA + " " + DATA);
	}

	@Test
	public void update() {
		UpdateCommand command = (UpdateCommand) service
				.convert(UPDATE + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertEquals("Should have set timestamp", TIMESTAMP, command.getTimestamp());
		assertEquals("Should have set data", DATA, command.getData());
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateTooManyArgs() {
		service.convert(UPDATE + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA + " fdasfdas");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateTooFewArgs() {
		service.convert(UPDATE + " " + ID_STR + " " + TIMESTAMP_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateInvalidId() {
		service.convert(UPDATE + " " + DATA + " " + TIMESTAMP_STR + " " + DATA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateInvalidTimestamp() {
		service.convert(UPDATE + " " + ID_STR + " " + DATA + " " + DATA);
	}

	@Test
	public void deleteIdOnly() {
		DeleteCommand command = (DeleteCommand) service.convert(DELETE + " " + ID_STR);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertNull("Shouldn't have set timestamp", command.getTimestamp());
		assertNull("Shouldn't have set data", command.getData());
	}

	@Test
	public void deleteWithTimestamp() {
		DeleteCommand command = (DeleteCommand) service.convert(DELETE + " " + ID_STR + " " + TIMESTAMP_STR);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertEquals("Should have set timestamp", TIMESTAMP, command.getTimestamp());
		assertNull("Shouldn't have set data", command.getData());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTooManyArgs() {
		service.convert(DELETE + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTooFewArgs() {
		service.convert(DELETE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteInvalidId() {
		service.convert(DELETE + " " + DATA + " " + TIMESTAMP_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteInvalidTimestamp() {
		service.convert(DELETE + " " + ID_STR + " " + DATA);
	}

	@Test
	public void get() {
		GetCommand command = (GetCommand) service.convert(GET + " " + ID_STR + " " + TIMESTAMP_STR);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertEquals("Should have set timestamp", TIMESTAMP, command.getTimestamp());
		assertNull("Shouldn't have set data", command.getData());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getTooManyArgs() {
		service.convert(GET + " " + ID_STR + " " + TIMESTAMP_STR + " " + DATA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getTooFewArgs() {
		service.convert(GET + " " + ID_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getInvalidId() {
		service.convert(GET + " " + DATA + " " + TIMESTAMP_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getInvalidTimestamp() {
		service.convert(GET + " " + ID_STR + " " + DATA);
	}

	@Test
	public void latest() {
		LatestCommand command = (LatestCommand) service.convert(LATEST + " " + ID_STR);
		assertNotNull("Didn't create command", command);
		assertEquals("Should have set ID", ID, command.getId());
		assertNull("Shouldn't have set timestamp", command.getTimestamp());
		assertNull("Shouldn't have set data", command.getData());
	}

	@Test(expected = IllegalArgumentException.class)
	public void latestTooManyArgs() {
		service.convert(LATEST + " " + ID_STR + " " + TIMESTAMP_STR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void latestTooFewArgs() {
		service.convert(LATEST);
	}

	@Test(expected = IllegalArgumentException.class)
	public void latestInvalidId() {
		service.convert(LATEST + " " + DATA);
	}

	@Test
	public void quit() {
		assertNull("Shouldn't have returned command", service.convert(QUIT));
	}
}
