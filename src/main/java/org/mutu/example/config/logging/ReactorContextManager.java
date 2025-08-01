package org.mutu.example.config.logging;

import reactor.util.context.Context;

/**
 * @author Zaw Than Oo
 * @since 30-July-2025 <br/>
 */
public class ReactorContextManager {
    private static final ThreadLocal<Context> threadContext = new ThreadLocal<>();

    public static void set(Context context) {
        threadContext.set(context);
    }

    public static Context getReactorContext() {
        return threadContext.get();
    }

    public static void clear() {
        threadContext.remove();
    }
}
