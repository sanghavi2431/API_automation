package framework.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import framework.context.ExecutionState;
import framework.context.JourneyContext;
import framework.context.JourneyType;
import framework.manager.JourneyManager;

/**
 * Base class for end-to-end journey tests with an isolated per-method journey
 * lifecycle.
 *
 * <p>Subclasses can use the protected {@link #context} and {@link #state}
 * fields after {@link #beforeMethod()} has initialized the current thread's
 * journey.</p>
 */
public abstract class BaseJourneyTest {

    /** Current test thread's journey data. */
    protected JourneyContext context;

    /** Current test thread's journey execution milestones. */
    protected ExecutionState state;

    /** Allows subclasses to select the journey type for each test method. */
    protected JourneyType getJourneyType() {
        return JourneyType.NEW_USER;
    }

    /** Starts a clean journey before every TestNG test method. */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        JourneyManager.start(getJourneyType());
        context = JourneyManager.context();
        state = JourneyManager.state();
    }

    /** Releases the current test thread's journey after every test method. */
    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        JourneyManager.finish();
        context = null;
        state = null;
    }
}
