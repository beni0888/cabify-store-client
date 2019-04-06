package com.jbenitoc.cabifystoreclient.infrastructure.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug("Outgoing Request: \n{\n\tURI         : {},\n\tMETHOD      : {},\n\tHEADERS     : {},\n\tBODY        : {}\n}",
                request.getURI(), request.getMethod(), request.getHeaders(), new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.debug("Incoming Response: \n{\n\tSTATUS CODE  : {},\n\tSTATUS TEXT  : {},\n\tHEADERS      : {},\n\tBODY         : {}\n}",
                response.getStatusCode(), response.getStatusText(), response.getHeaders(),
                StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
    }
}