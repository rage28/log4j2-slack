package com.github.rage28.log4j2.slack.appender;

import com.github.rage28.log4j2.slack.message.SlackLogMessage;
import com.github.rage28.log4j2.slack.model.SlackLog;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Field;
import com.github.seratch.jslack.api.webhook.Payload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Plugin(name = "Slack", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class SlackAppender extends AbstractAppender {
	private static final Slack slack = Slack.getInstance();
	private final String webhookUrl;
	private final String channel;
	private final String username;
	private final String iconEmoji;
	private final String iconUrl;

	SlackAppender(
		String name,
		Filter filter,
		Layout<? extends Serializable> layout,
		boolean ignoreExceptions,
		String webhookUrl,
		String channel,
		String username,
		String iconEmoji,
		String iconUrl
	) {
		super(name, filter, layout, ignoreExceptions);
		this.webhookUrl = webhookUrl;
		this.channel = channel;
		this.username = username;
		this.iconEmoji = iconEmoji;
		this.iconUrl = iconUrl;
	}

	@PluginBuilderFactory
	public static SlackAppenderBuilder createAppender() {
		return new SlackAppenderBuilder();
	}

	@Override
	public void append(LogEvent event) {
		if (event.getMessage() instanceof SlackLogMessage) {
			try {
				slack.send(webhookUrl, convertToPayload(event));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		} else {
			try {
				Payload payload = Payload.builder()
					.text((String) getLayout().toSerializable(event))
					.username(this.username)
					.build();

				slack.send(webhookUrl, payload);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	protected String getColorForLevel(Level level) {
		switch (level.name()) {
			case "TRACE":
			case "DEBUG":
				return "#67a0fc";

			case "WARN":
				return "warning";

			case "ERROR":
			case "FATAL":
				return "danger";

			case "INFO":
			default:
				return "good";
		}
	}

	protected Payload convertToPayload(LogEvent event) {
		SlackLog log = ((SlackLogMessage) event.getMessage()).getSlackLog();
		String color = getColorForLevel(event.getLevel());
		String iconEmoji = this.iconEmoji.isEmpty() ? ":robot_face:" : this.iconEmoji;

		if (!Pattern.compile("(:)\\w+(:)").matcher(iconEmoji).matches()) {
			iconEmoji = ":" + iconEmoji + ":";
		}

		if (log.getColor() != null && !log.getColor().isEmpty()) {
			color = log.getColor();
		}

		return Payload.builder()
			.username(this.username)
			.channel(this.channel)
			.iconEmoji(iconEmoji)
			.iconUrl(this.iconUrl)
			.attachments(Collections.singletonList(
				Attachment.builder()
					.fallback(log.getFallback())
					.color(color)
					.pretext(log.getPretext())
					.authorName(log.getAuthorName())
					.authorLink(log.getAuthorLink())
					.title(log.getTitle())
					.titleLink(log.getTitleLink())
					.text(log.getText())
					.fields((log.getSlackLogFields() != null) ? log.getSlackLogFields().stream()
						.map(f -> Field.builder()
							.title(f.getTitle())
							.value(f.getValue())
							.valueShortEnough(f.isShort())
							.build())
						.collect(Collectors.toList())
						: null)
					.imageUrl(log.getImageUrl())
					.thumbUrl(log.getThumbUrl())
					.footer(log.getFooter())
					.footerIcon(log.getFooterIcon())
					.ts(log.getTsString())
					.build()
			))
			.build();
	}

	public static class SlackAppenderBuilder implements org.apache.logging.log4j.core.util.Builder<SlackAppender> {
		@PluginBuilderAttribute
		@Required(message = "No name provided for ListAppender")
		private String name;

		@PluginElement("Layout")
		private Layout<? extends Serializable> layout;

		@PluginElement("Filter")
		private Filter filter;

		@PluginBuilderAttribute
		private boolean ignoreExceptions;

		@PluginBuilderAttribute
		@Required(message = "Slack Webhook URL required")
		private String webhookUrl;

		@PluginBuilderAttribute
		@Required(message = "Slack channel required")
		private String channel;

		@PluginBuilderAttribute
		@Required(message = "Slack username/bot-name required")
		private String username;

		@PluginBuilderAttribute
		private String iconEmoji;

		@PluginBuilderAttribute
		private String iconUrl;

		public SlackAppenderBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public SlackAppenderBuilder setLayout(Layout<? extends Serializable> layout) {
			this.layout = layout;
			return this;
		}

		public SlackAppenderBuilder setFilter(Filter filter) {
			this.filter = filter;
			return this;
		}

		public SlackAppenderBuilder setIgnoreExceptions(boolean ignoreExceptions) {
			this.ignoreExceptions = ignoreExceptions;
			return this;
		}

		public SlackAppenderBuilder setWebhookUrl(String webhookUrl) {
			this.webhookUrl = webhookUrl;
			return this;
		}

		public SlackAppenderBuilder setChannel(String channel) {
			this.channel = channel;
			return this;
		}

		public SlackAppenderBuilder setUsername(String username) {
			this.username = username;
			this.username = username;
			return this;
		}

		public SlackAppenderBuilder setIconEmoji(String iconEmoji) {
			this.iconEmoji = iconEmoji;
			return this;
		}

		public SlackAppenderBuilder setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
			return this;
		}

		@Override
		public SlackAppender build() {
			return new SlackAppender(name, filter, layout, ignoreExceptions, webhookUrl, channel, username, iconEmoji, iconUrl);
		}
	}
}
