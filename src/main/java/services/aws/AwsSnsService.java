package services.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwsSnsService {
    private final AmazonSNS snsClient;
    @Qualifier("catalogEventsTopic")
    private final Topic catalogTopic;

    public void pubish(MessageDto messageDto) {
        snsClient.publish(catalogTopic.getTopicArn(), messageDto.toString());
    }
}
