package org.mutu.example.config.logging;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

//@RestControllerAdvice
//@Order(-2) // Higher precedence than default
public class LoggingExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<String> handleException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromSupplier(() -> {
            // Capture context from exchange attributes
            Map<String, String> context = exchange.getAttributeOrDefault("log4j2.mdc", Map.of());
            ThreadContext.putAll(context);
            
            // Log the error (will include correlation ID)
            // Logger.error("Request processing error", ex);
            
            return "Internal Server Error";
        });
    }
}