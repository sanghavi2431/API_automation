package framework.base;

import framework.cache.ResponseCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import framework.context.ExecutionState;
import framework.context.JourneyContext;
import framework.context.JourneyType;
import framework.manager.JourneyManager;

/** Base class for TestNG tests that require a clean, thread-confined journey. */
public abstract class BaseTest {

	protected JourneyContext context;

	protected ExecutionState state;

	/** Allows subclasses to select a specific journey type. */
	protected JourneyType getJourneyType() {
		return JourneyType.NEW_USER;
	}

	/** Creates isolated journey state before every TestNG test invocation. */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {

		JourneyManager.start(getJourneyType());

		context = JourneyManager.context();

		state = JourneyManager.state();

	}

	/** Clears per-test caches and removes thread-local state after every test. */
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		ResponseCache.removeCurrentThreadCache();
		JourneyManager.finish();
		context = null;
		state = null;
	}

}
