package framework.context;

/**
 * Identifies the customer journey being executed by a test scenario.
 */
public enum JourneyType {

    /** A newly registered or first-time customer journey. */
    NEW_USER,

    /** A returning customer journey. */
    EXISTING_USER,

    /** A guest checkout journey. */
    GUEST,

    /** A member-specific journey. */
    MEMBER
}
