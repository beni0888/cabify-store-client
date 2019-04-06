package com.jbenitoc.cabifystoreclient.infrastructure.resttemplate;

import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        throw new UnexpectedError("RestTemplate error", new RuntimeException("This error should never happen"));
    }
}
