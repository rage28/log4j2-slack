package com.rage28.log4j2.slack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;

public class SlackManager extends AbstractManager {
	private final Logger logger = LogManager.getLogger(SlackManager.class);

	protected SlackManager(LoggerContext loggerContext, String name) {
		super(loggerContext, name);
	}
}
