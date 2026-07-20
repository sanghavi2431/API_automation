package framework.base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Shared support for TestNG data providers.
 *
 * <p>The helper creates an independent mutable data map per invocation. This
 * preserves compatibility with existing workflows, which add runtime values
 * such as tokens, cart IDs, and payment IDs to their input map.</p>
 */
public abstract class BaseDataProvider {

    /** Converts source rows into TestNG data-provider rows. */
    protected static Object[][] toDataProviderRows(List<Map<String, String>> sourceRows) {
        Objects.requireNonNull(sourceRows, "Source rows must not be null");

        return sourceRows.stream()
                .map(BaseDataProvider::copyRow)
                .map(row -> new Object[] {row})
                .toArray(Object[][]::new);
    }

    /** Creates a mutable defensive copy for workflow compatibility. */
    protected static Map<String, String> copyRow(Map<String, String> source) {
        return new LinkedHashMap<>(
                Objects.requireNonNull(source, "Source row must not be null"));
    }
}
