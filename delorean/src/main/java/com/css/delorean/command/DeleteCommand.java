/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;
import com.css.delorean.service.ConversionService;

/**
 * Deletes the observations for the id and timestamp in {@link TemporalDatastore}.
 * 
 * @author dell
 *
 */
public class DeleteCommand extends AbstractCommand {

	public DeleteCommand(Object id, Long timestamp) {
		super(id, timestamp, null);
		assert id != null;
	}

	@Override
	public String execute(TemporalDatastore datastore) {
		assert datastore != null;
		StringBuilder response = new StringBuilder("OK ");
		String result = null;
		if(getTimestamp() == null){
			result = datastore.delete(getId());
			if(result == null){
				throw new IllegalArgumentException(ConversionService.ERR_PREFIX + "There is no history for identifier '" + getId() + "'");
			}
			response.append(result);
		} else {
			result = datastore.delete(getId(), getTimestamp());
			if(result == null){
				throw new IllegalArgumentException(ConversionService.ERR_PREFIX + "There is no observation for identifier '" + getId() + "' as at timestamp '" + getTimestamp() + "'");
			}
			response.append(result);
		}

		return response.toString();
	}

}
