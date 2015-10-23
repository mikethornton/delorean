/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;
import com.css.delorean.service.ConversionService;

/**
 * Command which gets data from {@link TemporalDatastore} for the identifier at that timestamp
 * 
 * @author dell
 *
 */
public class GetCommand extends AbstractCommand {

	public GetCommand(Object id, Long timestamp) {
		super(id, timestamp, null);
		assert id != null;
		assert timestamp != null;
	}

	@Override
	public String execute(TemporalDatastore datastore) {
		assert datastore != null;
		String data = datastore.get(getId(), getTimestamp());

		if (data == null) {
			throw new IllegalArgumentException(ConversionService.ERR_PREFIX + "No history exists for Identifier '"
					+ getId() + "' at timestamp '" + getTimestamp() + "'");
		}

		return ConversionService.OK_PREFIX + data;
	}

}
