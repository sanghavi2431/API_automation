package workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import framework.context.ExecutionState;
import framework.context.ExecutionStateSupport;
import framework.context.JourneyContext;
import framework.context.JourneyContextSupport;
import framework.context.JourneyLifecycleLogger;
import framework.context.LegacyDataKeys;
import io.restassured.response.Response;
import services.AuthService;
import utils.ExtentReportManager;

/**
 * Coordinates authentication-related API journeys.
 *
 * <p>This flow preserves the existing map-based contract while synchronizing
 * successful authentication details with the thread-local journey framework.</p>
 */
public final class AuthenticationFlow {

    private static final String BASE_URL = "https://staging-api.woloo.in";
    private static final String MEDUSA_TOKEN_PATH = "results.medusa_token";
    private static final String CLIENT_TOKEN_PATH = "results.token";
    private static final String REGION_ID_PATH = "results.region_id";
    private static final String USER_ID_PATH = "results.user_id";

    private AuthenticationFlow() {
        // Utility class.
    }

    /**
     * Authenticates a user through the existing OTP login flow.
     *
     * <p>For backward compatibility, the supplied map is populated with
     * {@code medusa_token}, {@code clientToken}, {@code region_id}, and
     * {@code user_id}. The same values are stored in the current thread's
     * {@link JourneyContext}.</p>
     *
     * @param data mutable test data used by the existing authentication flow
     */
    public static void authenticate(Map<String, String> data) {
        Objects.requireNonNull(data, "Authentication data must not be null");

        JourneyLifecycleLogger.start("user authentication");
        Response response = AuthService.login(BASE_URL, data);

        String medusaToken = "Bearer " + response.jsonPath().getString(MEDUSA_TOKEN_PATH);
        String clientToken = response.jsonPath().getString(CLIENT_TOKEN_PATH);
        String regionId = response.jsonPath().getString(REGION_ID_PATH);
        String userId = response.jsonPath().getString(USER_ID_PATH);

        populateLegacyMap(data, medusaToken, clientToken, regionId, userId);
        populateJourneyContext(medusaToken, clientToken, regionId, userId);
        updateAuthenticationState(true);

        JourneyLifecycleLogger.complete("User authentication");
    }

    /**
     * Authenticates a user using a new mutable data map.
     *
     * <p>This overload is retained for journey-framework consumers that do not
     * need direct access to the legacy map-based authentication data.</p>
     */
    public static void authenticate() {
        Map<String, String> data = new HashMap<>();
        authenticate(data);
    }

    /**
     * Executes the existing invalid-OTP authentication scenario.
     *
     * @param data mutable test data used by the authentication flow
     */
    public static void invalidOTPError(Map<String, String> data) {
        Objects.requireNonNull(data, "Authentication data must not be null");

        data.put(LegacyDataKeys.MOBILE_NUMBER, "123456");
        data.put(LegacyDataKeys.OTP, "1235");

        ExtentReportManager.info("Starting invalid OTP authentication validation.");

        String requestId = AuthService.sendOtp(BASE_URL, data);
        data.put(LegacyDataKeys.REQUEST_ID, requestId);
        AuthService.verifyOtpError(BASE_URL, data);

        updateAuthenticationState(false);
        ExtentReportManager.pass("Invalid OTP authentication validation completed.");
    }

    /**
     * Executes the existing invalid-mobile-number authentication scenario.
     *
     * @param data mutable test data used by the authentication flow
     */
    public static void invalidMobileNoError(Map<String, String> data) {
        Objects.requireNonNull(data, "Authentication data must not be null");

        data.put(LegacyDataKeys.MOBILE_NUMBER, "xyz");

        ExtentReportManager.info(
                "Starting invalid mobile number authentication validation.");

        AuthService.sendOtpError(BASE_URL, data);

        updateAuthenticationState(false);

        ExtentReportManager.pass(
                "Invalid mobile number authentication validation completed.");
    }

    /**
     * Preserves the existing map keys expected by the current workflows and
     * service classes.
     */
    private static void populateLegacyMap(
            Map<String, String> data,
            String medusaToken,
            String clientToken,
            String regionId,
            String userId) {

        data.put(LegacyDataKeys.MEDUSA_TOKEN, medusaToken);
        data.put(LegacyDataKeys.CLIENT_TOKEN, clientToken);
        data.put(LegacyDataKeys.REGION_ID, regionId);
        data.put(LegacyDataKeys.USER_ID, userId);
    }

    /** Synchronizes successful authentication data with the current journey. */
    private static void populateJourneyContext(
            String medusaToken,
            String clientToken,
            String regionId,
            String userId) {

        JourneyContextSupport.setIfText(JourneyContext::setMedusaToken, medusaToken);
        JourneyContextSupport.setIfText(JourneyContext::setClientToken, clientToken);
        JourneyContextSupport.setIfText(JourneyContext::setRegionId, regionId);
        JourneyContextSupport.setIfText(JourneyContext::setUserId, userId);
    }

    /**
     * Updates the authentication milestone for the current thread's journey.
     *
     * @param authenticated {@code true} after successful authentication;
     *                      {@code false} for invalid authentication scenarios
     */
    private static void updateAuthenticationState(boolean authenticated) {
        ExecutionStateSupport.update(ExecutionState::setAuthenticated, authenticated);
    }
}
