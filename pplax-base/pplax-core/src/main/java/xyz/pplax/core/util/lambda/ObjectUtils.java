package xyz.pplax.core.util.lambda;

import java.util.function.Consumer;


public class ObjectUtils {

    public static <T> void ifNotNull(T t, Consumer<T> consumer) {
        if (t != null) {
            consumer.accept(t);
        }
    }
}
