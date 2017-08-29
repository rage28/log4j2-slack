package com.github.rage28.log4j2.slack.model;

public class SlackLogField {
	private String title;
	private String value;
	private boolean isShort;

	public String getTitle() {
		return title;
	}

	public SlackLogField setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getValue() {
		return value;
	}

	public SlackLogField setValue(String value) {
		this.value = value;
		return this;
	}

	public boolean isShort() {
		return isShort;
	}

	public SlackLogField setShort(boolean aShort) {
		isShort = aShort;
		return this;
	}
}
