/**
 * 
 */
package com.css.delorean.data;

/**
 * Implementors will guarantee that data is saved in time sequence according to timestamp for a given key.
 * 
 * @author dell
 *
 */
public interface TemporalDatastore {
	/**
	 * Creates a new history for a key with the first entry according to timestamp.
	 * 
	 * @param key Object
	 * @param timestamp long
	 * @param data String
	 * @return boolean false or true depending upon if there was already a history for this key or not. If there
	 * was then this entry will not have been entered and a new history sequence will not have been created.
	 */
	boolean create(Object key, long timestamp, String data);
	/**
	 * Update an existing history entry for this key. If there is an existing match for this timestamp then it will
	 * be updated, if not then a new history entry will be added. If no history exists for this key then a new one will be added.
	 * 
	 * @param key Object
	 * @param timestamp long
	 * @param data String
	 * @return String the pre existing history entries data for this key or null if there was no pre existing entry. 
	 */
	String update(Object key, long timestamp, String data);
	/**
	 * Delete all history for this key.
	 * 
	 * @param key
	 * @return String the data with the greatest timestamp, will return null if there is no history.
	 */
	String delete(Object key);
	/**
	 * Delete the history for this key from this timestamp onwards.
	 * 
	 * @param key
	 * @param timestamp
	 * @return String the current data as per the timestamp.
	 */
	String delete(Object key, long timestamp);
	/**
	 * Get the data from the observation for this key as per this timestamp
	 * 
	 * @param key
	 * @param timestamp
	 * @return String the data, or null if there is none for this timestamp.
	 */
	String get(Object key, long timestamp);
	/**
	 * Get the data for the latest timestamp for this key.
	 * 
	 * @param key
	 * @return Object[] containing timestamp and data for the latest entry for this key.
	 */
	Object[] getLatest(Object key);
}
