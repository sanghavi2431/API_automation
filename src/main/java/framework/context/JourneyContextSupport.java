package framework.context;

import java.util.Map;
import java.util.function.BiConsumer;

import framework.manager.JourneyManager;

/** Shared, null-safe operations for synchronizing legacy data with journey context. */
public final class JourneyContextSupport {

    private JourneyContextSupport() {
        // Utility class.
    }

    /** Returns the context associated with the current test thread. */
    public static JourneyContext current() {
        return JourneyManager.context();
    }

    /** Applies a non-blank value to a typed context setter. */
    public static void setIfText(
            BiConsumer<JourneyContext, String> setter,
            String value) {

        if (hasText(value)) {
            setter.accept(current(), value);
        }
    }

    /** Stores a non-blank dynamic context attribute. */
    public static void putIfText(String key, String value) {
        if (hasText(value)) {
            current().put(key, value);
        }
    }

    /** Removes a dynamic context attribute from the current journey. */
    public static void remove(String key) {
        current().remove(key);
    }

    /** Returns the first non-blank value, or {@code null} when neither is present. */
    public static String firstNonBlank(String primary, String fallback) {
        return hasText(primary) ? primary : fallback;
    }

    /** Resolves a value from legacy data, using the journey value as a fallback. */
    public static String resolveLegacyValue(
            Map<String, String> data,
            String key,
            String journeyValue) {

        String value = firstNonBlank(data.get(key), journeyValue);
        if (hasText(value) && !hasText(data.get(key))) {
            data.put(key, value);
        }
        return value;
    }

    /** Returns whether a value is non-null and non-blank. */
    public static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
