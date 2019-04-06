package com.jbenitoc.cabifystoreclient.infrastructure.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler errorHandler) {
        return builder.requestFactory(this::getClientHttpRequestFactory)
                .errorHandler(errorHandler)
                .additionalInterceptors(new RequestResponseLoggingInterceptor())
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(5000);
        return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
    }

}