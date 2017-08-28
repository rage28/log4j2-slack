package com.rage28.log4j2.slack.model;

import java.util.List;

public class SlackLog {
	private String fallback;
	private String color;
	private String pretext;
	private String authorName;
	private String authorLink;
	private String authorIcon;
	private String title;
	private String titleLink;
	private String text;
	private List<SlackLogField> slackLogFields;
	private String imageUrl;
	private String thumbUrl;
	private String footer;
	private String footerIcon;
	private long ts;

	public String getFallback() {
		return fallback;
	}

	public String getColor() {
		return color;
	}

	public String getPretext() {
		return pretext;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getAuthorLink() {
		return authorLink;
	}

	public String getAuthorIcon() {
		return authorIcon;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleLink() {
		return titleLink;
	}

	public String getText() {
		return text;
	}

	public List<SlackLogField> getSlackLogFields() {
		return slackLogFields;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public String getFooter() {
		return footer;
	}

	public String getFooterIcon() {
		return footerIcon;
	}

	public long getTs() {
		return ts;
	}

	public String getTsString() {
		return Long.toString(ts);
	}

	public static final class SlackLogBuilder {
		private String fallback;
		private String color;
		private String pretext;
		private String authorName;
		private String authorLink;
		private String authorIcon;
		private String title;
		private String titleLink;
		private String text;
		private List<SlackLogField> slackLogFields;
		private String imageUrl;
		private String thumbUrl;
		private String footer;
		private String footerIcon;
		private long ts;

		private SlackLogBuilder() {
		}

		public static SlackLogBuilder builder() {
			return new SlackLogBuilder();
		}

		public SlackLogBuilder withFallback(String fallback) {
			this.fallback = fallback;
			return this;
		}

		public SlackLogBuilder withColor(String color) {
			this.color = color;
			return this;
		}

		public SlackLogBuilder withPretext(String pretext) {
			this.pretext = pretext;
			return this;
		}

		public SlackLogBuilder withAuthorName(String authorName) {
			this.authorName = authorName;
			return this;
		}

		public SlackLogBuilder withAuthorLink(String authorLink) {
			this.authorLink = authorLink;
			return this;
		}

		public SlackLogBuilder withAuthorIcon(String authorIcon) {
			this.authorIcon = authorIcon;
			return this;
		}

		public SlackLogBuilder withTitle(String title) {
			this.title = title;
			return this;
		}

		public SlackLogBuilder withTitleLink(String titleLink) {
			this.titleLink = titleLink;
			return this;
		}

		public SlackLogBuilder withText(String text) {
			this.text = text;
			return this;
		}

		public SlackLogBuilder withSlackLogFields(List<SlackLogField> slackLogFields) {
			this.slackLogFields = slackLogFields;
			return this;
		}

		public SlackLogBuilder withImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public SlackLogBuilder withThumbUrl(String thumbUrl) {
			this.thumbUrl = thumbUrl;
			return this;
		}

		public SlackLogBuilder withFooter(String footer) {
			this.footer = footer;
			return this;
		}

		public SlackLogBuilder withFooterIcon(String footerIcon) {
			this.footerIcon = footerIcon;
			return this;
		}

		public SlackLogBuilder withTs(long ts) {
			this.ts = ts;
			return this;
		}

		public SlackLog build() {
			SlackLog slackLog = new SlackLog();
			slackLog.footerIcon = this.footerIcon;
			slackLog.imageUrl = this.imageUrl;
			slackLog.footer = this.footer;
			slackLog.color = this.color;
			slackLog.authorIcon = this.authorIcon;
			slackLog.slackLogFields = this.slackLogFields;
			slackLog.authorName = this.authorName;
			slackLog.thumbUrl = this.thumbUrl;
			slackLog.ts = this.ts;
			slackLog.pretext = this.pretext;
			slackLog.fallback = this.fallback;
			slackLog.authorLink = this.authorLink;
			slackLog.titleLink = this.titleLink;
			slackLog.title = this.title;
			slackLog.text = this.text;
			return slackLog;
		}
	}
}
