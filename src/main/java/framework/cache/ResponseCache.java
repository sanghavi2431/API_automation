package framework.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides a thread-local cache for data needed within a single test journey.
 *
 * <p>The public API remains compatible with the existing static cache API,
 * while storage is isolated per test thread to prevent parallel-test leakage.</p>
 */
public final class ResponseCache {

    private static final ThreadLocal<Map<String, Object>> CACHE =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    private ResponseCache() {
        // Utility class.
    }

    public static void put(String key, Object value) {
        CACHE.get().put(Objects.requireNonNull(key, "Cache key must not be null"),
                Objects.requireNonNull(value, "Cache value must not be null"));
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) CACHE.get().get(Objects.requireNonNull(key, "Cache key must not be null"));
    }

    public static boolean contains(String key) {
        return CACHE.get().containsKey(Objects.requireNonNull(key, "Cache key must not be null"));
    }

    public static void remove(String key) {
        CACHE.get().remove(Objects.requireNonNull(key, "Cache key must not be null"));
    }

    public static void clear() {
        CACHE.get().clear();
    }

    /** Clears and detaches the current thread's cache. */
    public static void removeCurrentThreadCache() {
        CACHE.remove();
    }
}
