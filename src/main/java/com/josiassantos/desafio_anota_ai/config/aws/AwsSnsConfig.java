package com.josiassantos.desafio_anota_ai.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsSnsConfig {
    @Value("${aws.region}")
    private String region;
    @Value("${aws.access-key-id}")
    private String accessKeyId;
    @Value("${aws.secret-key}")
    private String secretAccessKey;
    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    @Bean
    public SnsClient snsClient() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    @Bean(name = "catalogEventsTopicArn")
    public String catalogEventsTopicArn() {
        return catalogTopicArn;
    }
}
