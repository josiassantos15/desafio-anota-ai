package com.josiassantos.desafio_anota_ai.services.aws;

import com.josiassantos.desafio_anota_ai.AutoDisplayNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(AutoDisplayNameGenerator.class)
class AwsSnsServiceTest {
    private final String topicArn = "arn:aws:sns:us-east-1:123456789012:catalogEventsTopic";
    @Mock
    private SnsClient snsClient;
    @InjectMocks
    private AwsSnsService awsSnsService;
    @Captor
    private ArgumentCaptor<Consumer<PublishRequest.Builder>> publishRequestCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(awsSnsService, "catalogTopicArn", topicArn);
    }

    @Test
    void pubish_ShouldPublishEvent() {
        MessageDto messageDto = new MessageDto("mensagem");

        awsSnsService.pubish(messageDto);
        verify(snsClient, times(1)).publish(any(Consumer.class));
        verify(snsClient).publish(publishRequestCaptor.capture());
        PublishRequest.Builder builder = PublishRequest.builder();
        publishRequestCaptor.getValue().accept(builder);
        PublishRequest actualRequest = builder.build();

        assertAll(
                () -> assertEquals(topicArn, actualRequest.topicArn()),
                () -> assertEquals("mensagem", actualRequest.message()),
                () -> verifyNoMoreInteractions(snsClient)
        );
    }
}
