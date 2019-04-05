package com.jbenitoc.cabifystoreclient.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("cabify.store.api")
@Getter
@Setter
@ToString
public class StoreApiConfiguration {

    private String baseUrl;
    private Endpoints endpoint;

    @Getter
    @Setter
    @ToString
    public static class Endpoints {
        private String createCart;
        private String addItem;
        private String getTotal;
        private String deleteCart;
    }
}
