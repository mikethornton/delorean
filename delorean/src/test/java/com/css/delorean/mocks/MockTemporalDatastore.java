/**
 * 
 */
package com.css.delorean.mocks;

import com.css.delorean.data.TemporalDatastore;

/**
 * @author dell
 *
 */
public class MockTemporalDatastore implements TemporalDatastore{

	@Override
	public boolean create(Object key, long timestamp, String data) {
		return false;
	}

	@Override
	public String update(Object key, long timestamp, String data) {
		return null;
	}

	@Override
	public String delete(Object key) {
		return null;
	}

	@Override
	public String delete(Object key, long timestamp) {
		return null;
	}

	@Override
	public String get(Object key, long timestamp) {
		return null;
	}

	@Override
	public Object[] getLatest(Object key) {
		return null;
	}

}
