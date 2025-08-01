package org.mutu.example.config.logging;


import reactor.util.context.Context;

import java.util.List;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.ThreadContextDataInjector;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

public class ReactorContextDataInjector implements ContextDataInjector {

   private final ThreadContextDataInjector delegate = new ThreadContextDataInjector();

    @Override
    public StringMap injectContextData(List<Property> properties, StringMap reusable) {
        StringMap map = delegate.injectContextData(properties, reusable);
        Context context = ReactorContextManager.getReactorContext();
        if (context != null && context.hasKey("requestId")) {
            map.putValue("requestId", context.get("requestId").toString());
        }
        return map;
    }

    @Override
    public ReadOnlyStringMap rawContextData() {
        return delegate.rawContextData();
    }

}
