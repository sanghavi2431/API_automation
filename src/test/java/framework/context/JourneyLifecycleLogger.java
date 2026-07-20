package framework.context;

import utils.ExtentReportManager;

/** Standard lifecycle logging for journey-flow entry and successful completion. */
public final class JourneyLifecycleLogger {

    private JourneyLifecycleLogger() {
        // Utility class.
    }

    /** Logs the start of a journey flow. */
    public static void start(String flowName) {
        ExtentReportManager.info("Starting " + flowName + ".");
    }

    /** Logs successful completion of a journey flow. */
    public static void complete(String flowName) {
        ExtentReportManager.pass(flowName + " completed successfully.");
    }
}
