package com.auto.test;

import com.auto.utils.Constants;
import com.logigear.statics.Selaium;
import com.logigear.utils.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;


public class BrowserTestBase {
    private static final Logger log = LoggerFactory.getLogger(BrowserTestBase.class);
    Configuration config;

    @BeforeClass(alwaysRun = true)
    @BeforeMethod
    @Parameters("platform")
    public void beforeAll(@Optional String platform) {
        platform = java.util.Optional.ofNullable(platform).orElse("chrome");
        log.info("Running test on {}", platform);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        config = Configuration.defaultConfig(platform);
        config.setStartMaximized(true);
        config.setBaseUrl(Constants.BASE_URL);
        config.setCapabilities(options);
        Selaium.setConfig(config);
        Selaium.open("");
    }

    @AfterClass(alwaysRun = true)
    public void afterAll() {
    }
}
