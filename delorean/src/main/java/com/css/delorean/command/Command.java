/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;

/**
 * Command to be run on {@link TemporalDatastore}
 * 
 * @author dell
 *
 */
public interface Command {
	String execute(TemporalDatastore datastore);
}
