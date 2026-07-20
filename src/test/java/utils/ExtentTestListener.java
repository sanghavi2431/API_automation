package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class ExtentTestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {

        ExtentReportManager.initReport();
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentReportManager.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentReportManager.pass("Test Passed");
        ExtentReportManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentReportManager.fail(result.getThrowable());
        ExtentReportManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentReportManager.skip("Test Skipped");
        ExtentReportManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentReportManager.flushReport();
    }
}
//package utils;
//
//
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//
//
//public class ExtentTestListener implements ITestListener {
//
//    @Override
//    public void onStart(ITestContext context) {
//        ExtentReportManager.initReport();
//    }
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        ExtentReportManager.createTest(
//                result.getMethod().getMethodName(),
//                result.getMethod().getDescription()
//        );
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        ExtentReportManager.pass("Test Passed");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        ExtentReportManager.fail(
//                "Test Failed: " + result.getThrowable().getMessage(),
//                result.getThrowable()
//        );
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        ExtentReportManager.skip("Test Skipped");
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        ExtentReportManager.flushReport();
//    }
//}
//
