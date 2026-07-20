package framework.base;

import java.util.List;
import java.util.Map;

import framework.context.ExecutionState;
import framework.context.JourneyContext;

/**
 * Base type for feature-focused API test classes.
 *
 * <p>Journey setup and cleanup are inherited from {@link BaseJourneyTest}.
 * This class adds feature-test conveniences while retaining a stable extension
 * point for existing test classes.</p>
 */
public abstract class BaseFeatureTest extends BaseJourneyTest {

    /** Returns the current test method's thread-confined journey context. */
    protected JourneyContext context() {
        return context;
    }

    /** Returns the current test method's thread-confined execution state. */
    protected ExecutionState state() {
        return state;
    }

    /** Converts source rows into independent TestNG data-provider rows. */
    protected static Object[][] toDataProviderRows(List<Map<String, String>> sourceRows) {
        return BaseDataProvider.toDataProviderRows(sourceRows);
    }

    /** Creates a mutable defensive copy of a source data row. */
    protected static Map<String, String> copyRow(Map<String, String> source) {
        return BaseDataProvider.copyRow(source);
    }
}
