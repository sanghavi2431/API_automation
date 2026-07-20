package framework.context;

import java.util.function.BiConsumer;

import framework.manager.JourneyManager;

/** Shared operations for updating the current journey's execution milestones. */
public final class ExecutionStateSupport {

    private ExecutionStateSupport() {
        // Utility class.
    }

    /** Applies a milestone value to the execution state for the current test thread. */
    public static void update(
            BiConsumer<ExecutionState, Boolean> setter,
            boolean value) {

        setter.accept(JourneyManager.state(), value);
    }
}
