package io.camunda.connector.inbound;

import static org.assertj.core.api.Assertions.assertThat;

import io.camunda.connector.test.inbound.InboundConnectorContextBuilder;
import org.junit.jupiter.api.Test;

public class FileWatchConnectorTest {
    String eventToMonitor = "ENTRY_CREATE";
    String directory = "D:\\Desktop";
    String pollingInterval = "5";

    @Test
    void shouldFailWhenValidate_NoEventToMonitor() {
        // given
        var input = new MyConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        // when
        var connectorInput = context.bindProperties(MyConnectorProperties.class);

        // then
        assertThat(connectorInput)
                .isInstanceOf(MyConnectorProperties.class)
                .extracting("eventToMonitor")
                .isEqualTo("ENTRY_CREATE");
    }

    @Test
    void shouldFailWhenValidate_NoDirectory() {
        // given
        var input = new MyConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        // when
        var connectorInput = context.bindProperties(MyConnectorProperties.class);

        // then
        assertThat(connectorInput)
                .isInstanceOf(MyConnectorProperties.class)
                .extracting("directory")
                .isEqualTo("D:\\Desktop");
    }

    @Test
    void shouldFailWhenValidate_NoPollingInterval() {
        // given
        var input = new MyConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        // when
        var connectorInput = context.bindProperties(MyConnectorProperties.class);

        // then
        assertThat(connectorInput)
                .isInstanceOf(MyConnectorProperties.class)
                .extracting("pollingInterval")
                .isEqualTo("5");
    }
}
