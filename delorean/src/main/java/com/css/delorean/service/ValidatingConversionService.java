/**
 * 
 */
package com.css.delorean.service;

import com.css.delorean.command.Command;
import com.css.delorean.command.CreateCommand;
import com.css.delorean.command.DeleteCommand;
import com.css.delorean.command.GetCommand;
import com.css.delorean.command.LatestCommand;
import com.css.delorean.command.UpdateCommand;

/**
 * 
 * @author dell
 *
 */
public class ValidatingConversionService implements ConversionService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.css.delorean.service.ConversionService#convert(java.lang.String)
	 */
	@Override
	public Command convert(String input) {
		if (input == null || input.length() == 0) {
			throw new IllegalArgumentException("There was no input provided");
		}
		String[] inputCommand = input.trim().split("\\s+");
		String strAction = inputCommand[0];

		switch (strAction) {
		case "CREATE":
			if (inputCommand.length != 4) {
				throw new IllegalArgumentException(
						ERR_PREFIX + "The CREATE command should have the format: CREATE <id> <timestamp> <data>");
			}
			return new CreateCommand(parseIdentifier(inputCommand[1]), parseTimestamp(inputCommand[2]),
					inputCommand[3]);
		case "UPDATE":
			if (inputCommand.length != 4) {
				throw new IllegalArgumentException(
						ERR_PREFIX + "The UPDATE command should have the format: UPDATE <id> <timestamp> <data>");
			}
			return new UpdateCommand(parseIdentifier(inputCommand[1]), parseTimestamp(inputCommand[2]),
					inputCommand[3]);
		case "DELETE":
			if (inputCommand.length < 2 || inputCommand.length > 3) {
				throw new IllegalArgumentException(
						ERR_PREFIX + "The DELETE command should have the format: DELETE <id> [timestamp], where timestamp is non mandatory.");
			}
			return new DeleteCommand(parseIdentifier(inputCommand[1]),
					inputCommand.length == 3 ? parseTimestamp(inputCommand[2]) : null);
		case "GET":
			if (inputCommand.length != 3) {
				throw new IllegalArgumentException(ERR_PREFIX + "The GET command should have the format: GET <id> <timestamp>");
			}
			return new GetCommand(parseIdentifier(inputCommand[1]), parseTimestamp(inputCommand[2]));
		case "LATEST":
			if (inputCommand.length != 2) {
				throw new IllegalArgumentException(ERR_PREFIX + "The LATEST command should have the format: LATEST <id>");
			}
			return new LatestCommand(parseIdentifier(inputCommand[1]));
		case "QUIT":
			if (inputCommand.length != 1) {
				throw new IllegalArgumentException(ERR_PREFIX + "The QUIT command should have the format: QUIT");
			}
			return null;
		}

		throw new IllegalArgumentException(
				ERR_PREFIX + "Command " + strAction + " is unknown, please use CREATE, UPDATE, DELETE, GET, LATEST or QUIT.");
	}

	private Integer parseIdentifier(String input) {
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ERR_PREFIX + "Invalid identifier, must be integer.");
		}

	}

	private Long parseTimestamp(String input) {
		try {
			return Long.valueOf(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ERR_PREFIX + "Invalid timestamp, must be integer.");
		}
	}

}
