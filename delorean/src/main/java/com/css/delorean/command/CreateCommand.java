/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;
import com.css.delorean.service.ConversionService;

/**
 * CreateCommand that executes create on {@link TemporalDatastore}.
 * 
 * @author dell
 *
 */
public class CreateCommand extends AbstractCommand {

	public CreateCommand(Object id, Long timestamp, String data) {
		super(id, timestamp, data);
		assert id != null;
		assert timestamp != null;
		assert data != null;
	}

	@Override
	public String execute(TemporalDatastore datastore) {
		assert datastore != null;
		if (!datastore.create(getId(), getTimestamp(), getData())) {
			throw new IllegalArgumentException(ConversionService.ERR_PREFIX + "A history already exists for identifier '" + getId() + "'");
		}
		return "OK " + getData();
	}

}
