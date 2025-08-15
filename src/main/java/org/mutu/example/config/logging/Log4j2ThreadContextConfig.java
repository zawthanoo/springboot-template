package org.mutu.example.config.logging;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ThreadLocalAccessor;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Hooks;

import java.util.Map;

@Configuration
public class Log4j2ThreadContextConfig {

    @PostConstruct
    public void setup() {
        ContextRegistry registry = ContextRegistry.getInstance();
        
        registry.registerThreadLocalAccessor(new ThreadLocalAccessor<Map<String, String>>() {
            @Override
            public String key() {
                return "log4j2.mdc";
            }

            @Override
            public Map<String, String> getValue() {
                return ThreadContext.getImmutableContext();
            }

            @Override
            public void setValue(Map<String, String> values) {
                if (values == null || values.isEmpty()) {
                    ThreadContext.clearMap();
                } else {
                    ThreadContext.clearMap();
                    ThreadContext.putAll(values);
                }
            }

            @Override
            public void reset() {
                ThreadContext.clearMap();
            }
        });

        // Enable automatic context propagation
        Hooks.enableAutomaticContextPropagation();
    }
}