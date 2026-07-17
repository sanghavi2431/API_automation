//package utils;
//
//import java.util.Map;
//
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//
//import io.restassured.response.Response;
//
//public final class ApiExtentLogger {
//
//    private ApiExtentLogger() {
//        throw new IllegalStateException("Utility class");
//    }
//
//    /**
//     * Logs API request details into Extent Report.
//     *
//     * @param endpoint API endpoint
//     * @param method HTTP method
//     * @param headers Request headers
//     * @param body Request body
//     */
//    public static void logRequest(
//            String endpoint,
//            String method,
//            String headers,
//            String body) {
//
//        String requestLog =
//                "<b>ENDPOINT :</b><br>" + endpoint +
//                "<br><br>" +
//                "<b>METHOD :</b><br>" + method +
//                "<br><br>" +
//                "<b>HEADERS :</b><br>" + safe(headers) +
//                "<br><br>" +
//                "<b>REQUEST BODY :</b><br><pre>" +
//                safe(body) +
//                "</pre>";
//
//        ExtentReportManager.getTest()
//                .log(Status.INFO,
//                        "API REQUEST",
//                        MediaEntityBuilder
//                                .createScreenCaptureFromBase64String(
//                                        createHtmlSnippet(requestLog))
//                                .build());
//
//        ExtentReportManager.info(requestLog);
//    }
//
//    /**
//     * Logs API request with query parameters.
//     *
//     * @param endpoint API endpoint
//     * @param method HTTP method
//     * @param queryParams Query parameters
//     */
//    public static void logRequest(
//            String endpoint,
//            String method,
//            Map<String, Object> queryParams) {
//
//        String requestLog =
//                "<b>ENDPOINT :</b><br>" + endpoint +
//                "<br><br>" +
//                "<b>METHOD :</b><br>" + method +
//                "<br><br>" +
//                "<b>QUERY PARAMS :</b><br><pre>" +
//                safe(queryParams) +
//                "</pre>";
//
//        ExtentReportManager.info(requestLog);
//    }
//
//    /**
//     * Logs API response details.
//     *
//     * @param response RestAssured response
//     */
//    public static void logResponse(Response response) {
//
//        String responseLog =
//                "<b>STATUS CODE :</b><br>" +
//                response.statusCode() +
//                "<br><br>" +
//                "<b>RESPONSE TIME :</b><br>" +
//                response.time() + " ms" +
//                "<br><br>" +
//                "<b>RESPONSE BODY :</b><br><pre>" +
//                response.asPrettyString() +
//                "</pre>";
//
//        ExtentReportManager.info(responseLog);
//    }
//
//    /**
//     * Prevents null values from appearing in report.
//     *
//     * @param object Object to convert
//     * @return String representation
//     */
//    private static String safe(Object object) {
//        return object == null ? "N/A" : object.toString();
//    }
//
//    /**
//     * Generates small HTML snippet.
//     * Useful if screenshots/cards are needed later.
//     *
//     * @param html HTML content
//     * @return Base64 encoded HTML
//     */
//    private static String createHtmlSnippet(String html) {
//        return java.util.Base64.getEncoder()
//                .encodeToString(html.getBytes());
//    }
//}
package utils;

import java.util.Map;

import io.restassured.response.Response;

public final class ApiExtentLogger {

    private ApiExtentLogger() {
        throw new IllegalStateException("Utility class");
    }

    public static void logRequest(String endpoint,
                                  String method,
                                  String headers,
                                  String body) {

        String requestLog =
                "<b>ENDPOINT :</b><br>" + endpoint +
                "<br><br>" +
                "<b>METHOD :</b><br>" + method +
                "<br><br>" +
                "<b>HEADERS :</b><br><pre>" + safe(headers) + "</pre>" +
                "<br>" +
                "<b>REQUEST BODY :</b><br><pre>" + safe(body) + "</pre>";

        ExtentReportManager.info(requestLog);
    }

    public static void logRequest(String endpoint,
                                  String method,
                                  Map<String, Object> queryParams) {

        String requestLog =
                "<b>ENDPOINT :</b><br>" + endpoint +
                "<br><br>" +
                "<b>METHOD :</b><br>" + method +
                "<br><br>" +
                "<b>QUERY PARAMS :</b><br><pre>" + safe(queryParams) + "</pre>";

        ExtentReportManager.info(requestLog);
    }

    public static void logResponse(Response response) {

        String responseLog =
                "<b>STATUS CODE :</b><br>" + response.statusCode() +
                "<br><br>" +
                "<b>RESPONSE TIME :</b><br>" + response.time() + " ms" +
                "<br><br>" +
                "<b>RESPONSE BODY :</b><br><pre>" +
                response.asPrettyString() +
                "</pre>";

        ExtentReportManager.info(responseLog);
    }

    private static String safe(Object object) {
        return object == null ? "N/A" : object.toString();
    }
}