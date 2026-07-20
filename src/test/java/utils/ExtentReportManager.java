//package utils;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.aventstack.extentreports.*;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public final class ExtentReportManager {
//
//    private static ExtentReports extent;
//    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    private static final String REPORT_DIR = "executionReports";//"test-output";
//
//    private ExtentReportManager() {}
//
//    /* ================= INIT REPORT ================= */
//
//    public static synchronized void initReport() {
//
//        if (extent == null) {
//
//            File reportDir = new File(REPORT_DIR);
//
//            if (!reportDir.exists()) {
//                reportDir.mkdirs();
//            }
//
//            String timestamp =
//                    new SimpleDateFormat("yyyyMMdd_HHmmss")
//                            .format(new Date());
//
//            String reportPath =
//                    REPORT_DIR + File.separator +
//                    "API_ExtentReport_" + timestamp + ".html";
//
//            ExtentSparkReporter spark =
//                    new ExtentSparkReporter(reportPath);
//
//            spark.config().setDocumentTitle("API Automation Report");
//            spark.config().setReportName("REST Assured Execution Report");
//            spark.config().setTheme(Theme.DARK);
//
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//
//            extent.setSystemInfo("Framework", "REST Assured + TestNG");
//            extent.setSystemInfo("OS", System.getProperty("os.name"));
//            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
//            extent.setSystemInfo("Execution Time", timestamp);
//
//            System.out.println("Extent Report Initialized");
//        }
//    }
//
//    /* ================= CREATE TEST ================= */
//
//    public static synchronized void createTest(
//            String testName,
//            String description) {
//
//        ExtentTest extentTest =
//                extent.createTest(testName, description);
//
//        test.set(extentTest);
//    }
//
//    public static ExtentTest getTest() {
//        return test.get();
//    }
//
//    /* ================= LOGGING ================= */
//
//    public static void info(String message) {
//        getTest().info(message);
//    }
//
//    public static void pass(String message) {
//        getTest().pass(message);
//    }
//
//    public static void fail(Throwable throwable) {
//        getTest().fail(throwable);
//    }
//
//    public static void skip(String message) {
//        getTest().skip(message);
//    }
//
//    /* ================= REMOVE THREAD ================= */
//
//    public static void unload() {
//        test.remove();
//    }
//
//    /* ================= FLUSH REPORT ================= */
//
//    public static synchronized void flushReport() {
//
//        if (extent != null) {
//            extent.flush();
//            System.out.println("Extent Report Generated Successfully");
//        }
//    }
//}
package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReportManager {

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    private static final String REPORT_DIR =
            "executionReports";

    private ExtentReportManager() {
    }

    /* ================= INIT REPORT ================= */

    public static synchronized void initReport() {

        if (extent == null) {

            File reportDir = new File(REPORT_DIR);

            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());

            String reportPath =
                    REPORT_DIR + File.separator +
                            "API_ExtentReport_" +
                            timestamp +
                            ".html";

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle(
                    "API Automation Report");

            spark.config().setReportName(
                    "REST Assured Execution Report");

            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo(
                    "Framework",
                    "REST Assured + TestNG");

            extent.setSystemInfo(
                    "OS",
                    System.getProperty("os.name"));

            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version"));

            extent.setSystemInfo(
                    "Execution Time",
                    timestamp);

            System.out.println(
                    "Extent Report Initialized");
        }
    }

    /* ================= CREATE TEST ================= */

    public static synchronized void createTest(
            String testName,
            String description) {

        if (extent == null) {

            throw new IllegalStateException(
                    "Extent Report not initialized. " +
                    "Call initReport() first.");
        }

        ExtentTest extentTest =
                extent.createTest(
                        testName,
                        description);

        test.set(extentTest);
    }

    /* ================= GET CURRENT TEST ================= */

    public static ExtentTest getTest() {

        return test.get();
    }

    /* ================= LOGGING ================= */

    public static void info(String message) {

        ExtentTest extentTest = getTest();

        if (extentTest != null) {

            extentTest.info(message);

        } else {

            System.out.println(
                    "[INFO] " + message);
        }
    }

    public static void pass(String message) {

        ExtentTest extentTest = getTest();

        if (extentTest != null) {

            extentTest.pass(message);

        } else {

            System.out.println(
                    "[PASS] " + message);
        }
    }

    public static void fail(String message) {

        ExtentTest extentTest = getTest();

        if (extentTest != null) {

            extentTest.fail(message);

        } else {

            System.out.println(
                    "[FAIL] " + message);
        }
    }

    public static void fail(Throwable throwable) {

        ExtentTest extentTest = getTest();

        if (extentTest != null) {

            extentTest.fail(throwable);

        } else {

            throwable.printStackTrace();
        }
    }

    public static void skip(String message) {

        ExtentTest extentTest = getTest();

        if (extentTest != null) {

            extentTest.skip(message);

        } else {

            System.out.println(
                    "[SKIP] " + message);
        }
    }

    /* ================= REMOVE THREAD ================= */

    public static void unload() {

        test.remove();
    }

    /* ================= FLUSH REPORT ================= */

    public static synchronized void flushReport() {

        if (extent != null) {

            extent.flush();

            System.out.println(
                    "Extent Report Generated Successfully");
        }
    }
}
