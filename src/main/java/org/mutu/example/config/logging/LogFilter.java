package org.mutu.example.config.logging;

import java.nio.charset.StandardCharsets;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Order(2)
public class LogFilter implements WebFilter {
	
	@Autowired
	private LoggingService loggingService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse originalResponse = exchange.getResponse();
//		String responseBody = null;
		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				Flux<? extends DataBuffer> fluxBody = Flux.from(body);

				return super.writeWith(fluxBody.flatMap(dataBuffer -> {
					byte[] content = new byte[dataBuffer.readableByteCount()];
					dataBuffer.read(content);
					DataBufferUtils.release(dataBuffer); // always release!

					String responseBody = new String(content, StandardCharsets.UTF_8);
					byte[] newContent = responseBody.getBytes(StandardCharsets.UTF_8);
					DataBuffer buffer = originalResponse.bufferFactory().wrap(newContent);
					loggingService.logResponse(request.getMethod().name(), request.getURI().toString(), String.valueOf(originalResponse.getStatusCode()), originalResponse.getHeaders(), responseBody);
					return Mono.just(buffer);
				}));
			}
		};
		loggingService.logRequest(request.getMethod().name(), request.getURI().toString(), request.getHeaders(), request.getBody());
		return chain.filter(exchange.mutate().response(decoratedResponse).build());
	}
}


