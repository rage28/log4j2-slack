package com.github.rage28.log4j2.slack.layout;

import com.github.rage28.log4j2.slack.message.SlackLogMessage;
import org.apache.logging.log4j.core.Layout;

import java.nio.charset.Charset;

public interface StringifiedSlackLogLayout extends Layout<SlackLogMessage> {
	Charset getCharset();
}
