package framework.manager;

import java.util.Objects;

import framework.context.ExecutionState;
import framework.context.JourneyContext;
import framework.context.JourneyType;

/** Owns the thread-local lifecycle for one test journey. */
public final class JourneyManager {

    private static final ThreadLocal<JourneyContext> CONTEXT =
            ThreadLocal.withInitial(JourneyContext::new);

    private static final ThreadLocal<ExecutionState> STATE =
            ThreadLocal.withInitial(ExecutionState::new);

    private static final ThreadLocal<JourneyType> TYPE =
            new ThreadLocal<>();

    private JourneyManager() {
        // Utility class.
    }

    public static JourneyContext context() {
        return CONTEXT.get();
    }

    public static ExecutionState state() {
        return STATE.get();
    }

    public static JourneyType type() {
        return TYPE.get();
    }

    /** Returns whether a journey has been started for the current thread. */
    public static boolean isStarted() {
        return TYPE.get() != null;
    }

    /** Initializes clean journey state for the current thread. */
    public static void start(JourneyType journeyType) {
        TYPE.set(Objects.requireNonNull(journeyType, "Journey type must not be null"));
        CONTEXT.set(new JourneyContext());
        STATE.set(new ExecutionState());
    }

    /** Removes all thread-local journey state. This method is idempotent. */
    public static void finish() {
        CONTEXT.remove();
        STATE.remove();
        TYPE.remove();
    }
}
