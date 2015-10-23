/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;

/**
 * Update Command that executes an update on {@link TemporalDatastore}.
 * 
 * @author dell
 *
 */
public class UpdateCommand extends AbstractCommand {

	public UpdateCommand(Object id, Long timestamp, String data) {
		super(id, timestamp, data);
		assert id != null;
		assert timestamp != null;
		assert data != null;
	}

	@Override
	public String execute(TemporalDatastore datastore) {
		assert datastore != null;
		String data = datastore.update(getId(), getTimestamp(), getData());

		return "OK " + data;
	}

}
