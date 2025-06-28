package com.josiassantos.desafio_anota_ai.services.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
@RequiredArgsConstructor
public class AwsSnsService {
    private final SnsClient snsClient;
    @Qualifier("catalogEventsTopicArn")
    private final String catalogTopicArn;

    public void pubish(MessageDto messageDto) {
        snsClient.publish(publish -> publish
                .topicArn(catalogTopicArn)
                .message(messageDto.message())
        );
    }
}
