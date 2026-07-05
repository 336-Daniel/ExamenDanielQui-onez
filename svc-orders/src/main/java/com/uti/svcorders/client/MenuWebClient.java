package com.uti.svcorders.client;

import com.uti.svcorders.dto.DishResponse;
import com.uti.svcorders.exception.MenuServiceException;
import com.uti.svcorders.exception.ResourceNotfoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class MenuWebClient {

    private final WebClient webClient;

    public MenuWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public DishResponse getDishById(Long dishId) {
        log.info("WebClient - llamando svc-menu: GET {/api/menu/dishes/{}}", dishId);
        try {
            return webClient
                    .get()
                    .uri("/api/menu/dishes/{id}", dishId)
                    .retrieve()
                    .onStatus(
                            status -> status.value() == 404,
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new ResourceNotfoundException(
                                            "Plato no encontrado en el svc-menu con id: " + dishId
                                    ))
                    )
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new MenuServiceException(
                                            "Error de cliente desde svc-menu: " + body
                                    ))
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new MenuServiceException(
                                            "Error de servidor desde svc-menu: " + body
                                    ))
                    )
                    .bodyToMono(DishResponse.class)
                    .block();

        } catch (WebClientResponseException ex) {
            log.error("WebClient - Error HTTP desde svc-menu: {} {}", ex.getStatusCode(), ex.getMessage());
            throw new MenuServiceException(
                    "Error del cliente al llamar el svc-menu: " + ex.getMessage(), ex);

        } catch (Exception ex) {
            log.error("WebClient - no se logro conectar con svc-menu: {}", ex.getMessage());
            throw new MenuServiceException(
                    "No se logro conectar a svc-menu: " + ex.getMessage(), ex);
        }
    }
}