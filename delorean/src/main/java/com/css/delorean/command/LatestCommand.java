/**
 * 
 */
package com.css.delorean.command;

import com.css.delorean.data.TemporalDatastore;
import com.css.delorean.service.ConversionService;

/**
 * Command which gets the latest data on {@link TemporalDatastore} for this identifier.
 * 
 * @author dell
 *
 */
public class LatestCommand extends AbstractCommand {

	public LatestCommand(Object id) {
		super(id, null, null);
		assert id != null;
	}

	@Override
	public String execute(TemporalDatastore datastore) {
		assert datastore != null;
		Object[] datas = datastore.getLatest(getId());
		if(datas == null){
			throw new IllegalArgumentException(ConversionService.ERR_PREFIX + "No history exists for Identifier '"
					+ getId() + "'");
		}
		
		StringBuilder response = new StringBuilder(ConversionService.OK_PREFIX);
		for (Object data : datas) {
			response.append(data);
			response.append(" ");
		}

		return response.toString();
	}

}
