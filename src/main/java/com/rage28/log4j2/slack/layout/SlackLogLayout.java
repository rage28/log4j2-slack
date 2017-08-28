package com.rage28.log4j2.slack.layout;

import com.rage28.log4j2.slack.message.SlackLogMessage;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.AbstractLayout;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Plugin(name = "SlackLogLayout", category = Core.CATEGORY_NAME, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class SlackLogLayout extends AbstractLayout<SlackLogMessage> implements StringifiedSlackLogLayout {

	static final String CONTENT_TYPE = "text/plain";
	private final String charsetName;
	private transient Charset charset;

	private SlackLogLayout(Configuration configuration, final Charset charset) {
		super(configuration, null, null);
		this.charset = (charset == null) ? StandardCharsets.UTF_8 : charset;
		this.charsetName = this.charset.name();
	}

	@PluginBuilderFactory
	public static <B extends Builder<B>> B newBuilder() {
		return new Builder<B>().asBuilder();
	}

	@Override
	public byte[] toByteArray(LogEvent event) {
		return getBytes(toSerializable(event));
	}

	@Override
	public SlackLogMessage toSerializable(LogEvent event) {
		return (SlackLogMessage) event.getMessage();
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

	protected byte[] getBytes(final SlackLogMessage log) {
		try {
			return log.getFormattedMessage().getBytes(charsetName);
		} catch (final UnsupportedEncodingException e) {
			return log.getFormattedMessage().getBytes(charset);
		}
	}

	public static class Builder<B extends Builder<B>> extends AbstractLayout.Builder<B> implements org.apache.logging.log4j.core.util.Builder<SlackLogLayout> {
		@PluginBuilderAttribute(value = "charset")
		private Charset charset;

		public Charset getCharset() {
			return charset;
		}

		public B setCharset(final Charset charset) {
			this.charset = charset;
			return asBuilder();
		}

		@Override
		public SlackLogLayout build() {
			return new SlackLogLayout(getConfiguration(), getCharset());
		}
	}
}
