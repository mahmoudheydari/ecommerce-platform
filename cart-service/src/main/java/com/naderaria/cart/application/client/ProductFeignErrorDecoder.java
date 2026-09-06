package com.naderaria.cart.application.client;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try{
            if(response.body()!=null){
                try(InputStream inputStream = response.body().asInputStream()){
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    log.debug("Feign error body from product-service:{}",body);

                    JsonNode node = objectMapper.readTree(body);

                    String errorCodeText = null;
                    if(node.has("errorCode")){
                        errorCodeText = node.get("errorCode").asText();
                    }

                    if(errorCodeText != null){
                        ErrorCode errorCode = ErrorCode.valueOf(errorCodeText);
                        return new BusinessException(errorCode);
                    }
                }
            }
        }catch(Exception ex){
            log.warn("Failed to decode product-service error response", ex);
        }

        if(response.status() == 404){
            return new BusinessException(ErrorCode.ProductNotFoundException);
        }

        return defaultErrorDecoder.decode(methodKey,response);
    }
}