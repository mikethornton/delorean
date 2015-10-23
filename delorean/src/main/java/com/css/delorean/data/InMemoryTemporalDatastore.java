/**
 * 
 */
package com.css.delorean.data;

import java.util.HashMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * In memory implementation of {@link TemporalDatastore} making use of HashMap and
 * TreeSet to order entries according to key. Makes use of {@link ReentrantReadWriteLock}
 * to ensure safety.
 * 
 * @author dell
 *
 */
public class InMemoryTemporalDatastore implements TemporalDatastore {
	private HashMap<Object, NavigableSet<TemporalObservation>> store = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	public boolean create(Object key, long timestamp, String data) {
		rwLock.writeLock().lock();
		try {
			if (exists(key)) {
				return false;
			}

			NavigableSet<TemporalObservation> history = getHistory(key);
			return history.add(new TemporalObservation(timestamp, data));
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public String update(Object key, long timestamp, String data) {
		rwLock.writeLock().lock();
		try {
			// Assumption made that if there are no existing histories then we
			// act as create
			NavigableSet<TemporalObservation> history = getHistory(key);
			TemporalObservation newTo = new TemporalObservation(timestamp, data);

			TemporalObservation floor = history.floor(newTo);

			if (floor == null) {
				history.add(newTo);
				return null;
			}
			if (floor.getTimestamp() == newTo.getTimestamp()) {
				history.remove(floor);
			}
			history.add(newTo);
			return floor.getObservation();
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	private NavigableSet<TemporalObservation> getHistory(Object key) {
		NavigableSet<TemporalObservation> history = store.get(key);
		if (history == null) {
			history = new TreeSet<>();
			store.put(key, history);
		}
		return history;
	}

	public String delete(Object key) {
		rwLock.writeLock().lock();
		try {
			if (!exists(key)) {
				return null;
			}
			NavigableSet<TemporalObservation> history = store.remove(key);
			TemporalObservation last = history.last();
			return last.getObservation();
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public String delete(Object key, long timestamp) {
		rwLock.writeLock().lock();
		try {
			if (!exists(key)) {
				return null;
			}
			NavigableSet<TemporalObservation> history = store.get(key);
			Set<TemporalObservation> tosToRemove = history.stream().filter(to -> to.getTimestamp() >= timestamp)
					.collect(Collectors.toSet());

			history.removeAll(tosToRemove);
			
			if(history.isEmpty()){
				store.remove(key);
				return null;
			}

			TemporalObservation last = history.last();
			return last.getObservation();
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public String get(Object key, long timestamp) {
		rwLock.readLock().lock();
		try {
			NavigableSet<TemporalObservation> history = store.get(key);
			if (history == null) {
				return null;
			}
			TemporalObservation to = history.floor(new TemporalObservation(timestamp, ""));
			if (to == null) {
				return null;
			}
			return to.getObservation();
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public Object[] getLatest(Object key) {
		rwLock.readLock().lock();
		try {
			NavigableSet<TemporalObservation> history = store.get(key);
			if (history == null || history.isEmpty()) {
				return null;
			}
			TemporalObservation to = history.last();
			return new Object[] { to.getTimestamp(), to.getObservation() };
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public boolean exists(Object key) {
		return store.containsKey(key);
	}

	private static class TemporalObservation implements Comparable<TemporalObservation> {
		private long timestamp;
		private String observation;

		public TemporalObservation(long timestamp, String observation) {
			super();
			assert observation != null;
			this.timestamp = timestamp;
			this.observation = observation;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public String getObservation() {
			return observation;
		}

		public int compareTo(TemporalObservation to) {
			return (int) (this.timestamp - to.timestamp);
		}
	}
}
