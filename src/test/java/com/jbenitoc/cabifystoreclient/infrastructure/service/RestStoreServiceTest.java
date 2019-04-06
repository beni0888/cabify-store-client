package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import com.jbenitoc.cabifystoreclient.infrastructure.configuration.StoreApiConfiguration;
import com.jbenitoc.cabifystoreclient.infrastructure.configuration.StoreApiConfiguration.Endpoints;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.AddItemToCartRequest;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestStoreServiceTest {

    private final static String CART_ID = randomUUID().toString();

    private StoreService storeService;
    private RestTemplate restTemplate;
    private StoreApiConfiguration configuration;
    private ApiResponseHandler apiResponseHandler;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        configuration = new StoreApiConfiguration();
        setUpApiConfiguration(configuration);
        apiResponseHandler = mock(ApiResponseHandler.class);
        storeService = new RestStoreService(restTemplate, configuration, apiResponseHandler);
    }

    private void setUpApiConfiguration(StoreApiConfiguration configuration) {
        configuration.setBaseUrl("http://localhost:8080");
        Endpoints endpointsConfiguration = new Endpoints();
        endpointsConfiguration.setCreateCart("/cart");
        endpointsConfiguration.setAddItem("/cart/{cartId}/item");
        endpointsConfiguration.setGetTotal("/cart/{cartId}");
        endpointsConfiguration.setDeleteCart("/cart/{cartId}");

        configuration.setEndpoint(endpointsConfiguration);
    }

    private ResponseEntity<String> responseEntity(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    @Test
    void whenCreateCart_thenItWorks() {
        String url = "http://localhost:8080/cart";
        ResponseEntity<String> responseEntity = responseEntity(HttpStatus.CREATED);
        CreateCartResponse createCartResponse = new CreateCartResponse(CART_ID);

        when(restTemplate.postForEntity(url, null, String.class)).thenReturn(responseEntity);
        when(apiResponseHandler.parseToObject(responseEntity, CreateCartResponse.class)).thenReturn(createCartResponse);

        Cart cart = storeService.createCart();

        assertThat(cart).isNotNull();
        assertThat(cart.getId()).isEqualTo(CART_ID);
    }

    @Test
    void whenAddItemToCart_thenItWorks() {
        String itemCode = "VOUCHER";
        int quantity = 1;
        AddItemToCartRequest request = new AddItemToCartRequest(itemCode, quantity);
        String url = "http://localhost:8080/cart/{cartId}/item";
        ResponseEntity<String> responseEntity = responseEntity(HttpStatus.CREATED);

        when(restTemplate.postForEntity(url, request, String.class, CART_ID)).thenReturn(responseEntity);

        storeService.addItemToCart(CART_ID, itemCode, quantity);

        verify(restTemplate, times(1)).postForEntity(url, request, String.class, CART_ID);
        verify(apiResponseHandler, times(1)).parseToObject(responseEntity, Void.class);
    }

    @Test
    void givenNullCartId_whenAddItemToCart_thenItThrowsAnException() {
        assertThrows(NullPointerException.class, () -> storeService.addItemToCart(null, "VOUCHER", 1));
    }

    @Test
    void givenNullItemCode_whenAddItemToCart_thenItThrowsAnException() {
        assertThrows(NullPointerException.class, () -> storeService.addItemToCart(CART_ID, null, 1));
    }

    @Test
    void whenGetTotalAmount_thenItWorks() {
        String url = "http://localhost:8080/cart/{cartId}";
        ResponseEntity<String> responseEntity = responseEntity(HttpStatus.OK);
        GetCartTotalAmountResponse getCartTotalAmountResponse = new GetCartTotalAmountResponse(CART_ID, "10.50");

        when(restTemplate.getForEntity(url, String.class, CART_ID)).thenReturn(responseEntity);
        when(apiResponseHandler.parseToObject(responseEntity, GetCartTotalAmountResponse.class))
                .thenReturn(getCartTotalAmountResponse);

        Price price = storeService.getTotalAmount(CART_ID);

        assertThat(price).isNotNull();
        assertThat(price.getValue()).isEqualTo(getCartTotalAmountResponse.totalAmount);
    }

    @Test
    void givenNullCartId_whenGetTotalAmount_thenItThrowsAnException() {
        assertThrows(NullPointerException.class, () -> storeService.getTotalAmount(null));
    }

    @Test
    void whenDeleteCart_thenItWorks() {
        String url = "http://localhost:8080/cart/{cartId}";
        ResponseEntity<String> responseEntity = responseEntity(HttpStatus.NO_CONTENT);

        when(restTemplate.exchange(url, HttpMethod.DELETE, null, String.class, CART_ID)).thenReturn(responseEntity);

        storeService.deleteCart(CART_ID);

        verify(apiResponseHandler, times(1)).parseToObject(responseEntity, Void.class);
    }

    @Test
    void givenNullCartId_whenDeleteCart_thenItThrowsAnException() {
        assertThrows(NullPointerException.class, () -> storeService.deleteCart(null));
    }
}
