package com.github.rage28.log4j2.slack.message;

import com.github.rage28.log4j2.slack.model.SlackLog;
import org.apache.logging.log4j.message.Message;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class SlackLogMessage implements Message, ISlackLogMessage {
	private final SlackLog log;
	private final DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
		.withLocale(Locale.getDefault())
		.withZone(ZoneOffset.systemDefault());

	public SlackLogMessage(SlackLog log) {
		this.log = log;
	}

	@Override
	public String getFormattedMessage() {
		StringBuilder builder = new StringBuilder();

		if (log.getAuthorName() != null && !log.getAuthorName().isEmpty()) {
			builder.append(log.getAuthorName()).append(" says ");
		}

		if (log.getTitle() != null && !log.getTitle().isEmpty()) {
			builder.append(log.getTitle()).append(" ");
		}

		if (log.getTitleLink() != null && !log.getTitleLink().isEmpty()) {
			builder.append("[").append(log.getTitleLink()).append("] ");
		}

		if (log.getTs() > 0) {
			builder.append(" @ ").append(df.format(Instant.ofEpochSecond(log.getTs())));
		}

		return builder.toString();
	}

	@Override
	public String getFormat() {
		return log.toString();
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{log};
	}

	@Override
	public Throwable getThrowable() {
		return null;
	}

	@Override
	public SlackLog getSlackLog() {
		return log;
	}

	@Override
	public String toString() {
		return this.getFormattedMessage();
	}
}
