/**
 * 
 */
package com.css.delorean.service;

import com.css.delorean.command.Command;

/**
 * ConversionService that converts the input into a {@link Command}.
 * 
 * @author dell
 *
 */
public interface ConversionService {
	String OK_PREFIX = "OK ";
	String ERR_PREFIX = "ERR ";
	/**
	 * Converts the input into a {@link Command}. Should throw IllegalArgumentException
	 * for invalid inputs.
	 * 
	 * @param input String
	 * @return Command
	 */
	Command convert(String input);
}
