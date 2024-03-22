package com.ll.sb20240322.global.meilisearch;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeilisearchConfig {
    @Bean
    public Client meilisearchClient() {
         return new Client(new Config("http://localhost:7700", "masterKey"));
    }
}
