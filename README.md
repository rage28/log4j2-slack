# Log4j2 Slack Appender

This is a highly customizable appender for [Apache SLF4J2](https://logging.apache.org/log4j/2.x/) to push the logs to Slack using [webhooks](https://api.slack.com/incoming-webhooks)

## Dependencies

Gradle
```groovy
    dependencies {
        compile 'com.rage28.log4j2:slack-appender:1.0'
    }
```
Maven
```xml
    <dependency>
        <groupId>com.rage28.log4j2</groupId>
        <artifactId>slack-appender</artifactId>
        <version>1.0</version>
    </dependency>
```

## Configuration

Example
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" packages="com.rage28.log4j2">
    <Appenders>
        <Slack name="ExampleSlacker"
                       channel="#updates"
                       webhookUrl="https://hooks.slack.com/services/X/Y/Z"
                       username="botter"
                       iconEmoji="robot_face">
            <SlackLogLayout />
        </Slack>
    </Appenders>

    <Loggers>
        <Root level="info">
            <AppenderRef ref="ExampleSlacker"/>
        </Root>
    </Loggers>
</Configuration>
```

```java
import com.rage28.log4j2.slack.message.SlackLogMessage;
import com.rage28.log4j2.slack.model.SlackLog.SlackLogBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyClass {
	private static Logger logger = LogManager.get(MyClass.class);
	
	public static void main(String[] args) {
		logger.error(new SlackLogMessage(SlackLogBuilder.builder()
                     			.withTitle("This is a title")
                     			.withColor("red")
                     			.withText("Something bad happened! I can feel it")
                     			.build()));
	}
}
```
