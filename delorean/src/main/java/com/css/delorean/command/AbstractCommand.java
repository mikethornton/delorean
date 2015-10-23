/**
 * 
 */
package com.css.delorean.command;

/**
 * @author dell
 *
 */
public abstract class AbstractCommand implements Command {
	private Object id;
	private Long timestamp;
	private String data;
	public AbstractCommand(Object id, Long timestamp, String data) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.data = data;
	}

	public Object getId() {
		return id;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public String getData() {
		return data;
	}
}
