package com.studi.payment.consumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class PreferenceService {
    private static final String MERCADO_PAGO_URL = "https://api.mercadopago.com/checkout/preferences";
    private final WebClient webClient;
    private final String accessToken;
    private final ObjectMapper objectMapper;

    public PreferenceService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(MERCADO_PAGO_URL).build();
        this.accessToken = System.getenv("MERCADOPAGO_ACCESS_TOKEN");
        this.objectMapper = objectMapper;
    }

    public String createPreference(double amount, String payerEmail) {
        Map<String, Object> payload = Map.of(
                "items", new Object[]{
                        Map.of(
                                "title", "Produto Exemplo",
                                "quantity", 1,
                                "unit_price", amount,
                                "currency_id", "BRL"
                        )
                },
                "payer", Map.of("email", payerEmail)
        );

        try {
            String responseJson = webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(status -> status.isError(), response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("Erro na API do Mercado Pago: " + errorBody))))
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(responseJson);

            if (jsonNode.has("id")) {
                return jsonNode.get("id").asText();
            } else {
                throw new RuntimeException("Resposta inválida: 'id' não encontrado.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar preferência: " + e.getMessage(), e);
        }
    }
}