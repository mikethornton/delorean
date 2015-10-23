/**
 * 
 */
package com.css.delorean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.css.delorean.command.Command;
import com.css.delorean.data.InMemoryTemporalDatastore;
import com.css.delorean.data.TemporalDatastore;
import com.css.delorean.service.ConversionService;
import com.css.delorean.service.ValidatingConversionService;

/**
 * Project Delorean coding challenge.
 * 
 * @author dell
 *
 */
public class Delorean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TemporalDatastore dataStore = new InMemoryTemporalDatastore();
		ConversionService conversionService = new ValidatingConversionService();

		try (PrintWriter writer = new PrintWriter(System.out);
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			writer.println("Welcome to Delorean, please enter your commands.");
			writer.flush();
			while (true) {
				try {
					String input = reader.readLine();
					Command command = conversionService.convert(input);
					if (command == null) {
						return;
					}
					String response = command.execute(dataStore);
					writer.println(response);
					writer.flush();
				} catch (IllegalArgumentException e) {
					writer.println(e.getMessage());
					writer.flush();
				}
			}
		} catch (IOException e) {
			//Unexpected IO Exception
			return;
		}
	}

}
