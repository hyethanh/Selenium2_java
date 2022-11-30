package com.auto.testng;

import com.auto.utils.Constants;
import com.auto.utils.ExecutionContext;
import com.auto.utils.FileUtils;
import com.logigear.statics.Selaium;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class TestListener implements ITestListener, IHookable {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("Tests started on {}", context.getCurrentXmlTest().getParameter("platform"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test case \"{} - {}\" is started", result.getMethod().getMethodName(),
                result.getMethod().getDescription());

    }

    @Override
    public void onFinish(ITestContext context) {
        // Add Environment information to Allure report
        Properties properties = new Properties();
        properties.putAll(ExecutionContext.getEnvironments());
        FileUtils.savePropertiesToFile(properties, Constants.ENV_ALLURE_FILE);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            try {
                File f = Selaium.takeScreenShot(OutputType.FILE);
                FileUtils.copyFile(f, new File(Constants.SCREENSHOT + System.nanoTime() + ".png"));
            } catch (Exception ex) {
                log.error("Error occurred", ex);
            }
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Submit test result into TestRail
        addResultTestRail(result);
    }

    private void addResultTestRail(ITestResult result) {

    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "Failure in case {0}", type = "image/png")
    private byte[] takeScreenShot(String methodName) throws IOException {
        return Selaium.takeScreenShot(OutputType.BYTES);
    }
}
