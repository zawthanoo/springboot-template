package org.mutu.example.config.logging;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Zaw Than Oo
 * @since 30-July-2025 <br/>
 */
@Component
public class LoggingFilter implements WebFilter {
	@Value("${header-key-list.log4jThreadContextList}")
	private String log4jThreadContextList;

	@Autowired
	private LoggingService loggingService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse originalResponse = exchange.getResponse();
		DataBufferFactory bufferFactory = originalResponse.bufferFactory();

		ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(request) {
			@Override
			public Flux<DataBuffer> getBody() {
				return super.getBody().doOnNext(dataBuffer -> {
					byte[] bytes = new byte[dataBuffer.readableByteCount()];
					dataBuffer.read(bytes);
					DataBufferUtils.release(dataBuffer);
					String requestBody = new String(bytes, StandardCharsets.UTF_8);
					loggingService.logRequest(request.getMethod().name(), request.getURI().getPath(),
							request.getHeaders(), requestBody);
				});
			}
		};

		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					return super.writeWith(((Flux<? extends DataBuffer>) body).map(dataBuffer -> {
						byte[] bytes = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(bytes);
						DataBufferUtils.release(dataBuffer);
						String responseBody = new String(bytes, StandardCharsets.UTF_8);
						loggingService.logResponse(request.getMethod().name(), request.getURI().getPath(),
								getStatusCode().toString(), getHeaders(), responseBody);
						return bufferFactory.wrap(bytes);
					}));
				}
				return super.writeWith(body);
			}
		};

		return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build())
				.contextWrite(ctx -> {
					if (!StringUtils.isEmpty(log4jThreadContextList)) {
						List<String> items = Arrays.asList(log4jThreadContextList.split("\\s*,\\s*"));
						for (String item : items) {
							ctx.put(item, request.getHeaders().getFirst(item));
						}
					}
					return ctx;
				});
	}
}
