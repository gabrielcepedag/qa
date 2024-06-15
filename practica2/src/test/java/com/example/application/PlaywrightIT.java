package com.example.application;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class PlaywrightIT {
    private static Playwright playwright;
    private static Browser browser;
    protected Page page;
    private BrowserContext browserContext;

    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.headless = false;
        browser = browserType.launch(launchOptions);
    }

    @AfterAll
    static void afterAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void beforeEach() {
        browserContext = browser.newContext();
        // Start tracing before creating / navigating a page.
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = browserContext.newPage();
    }

    @AfterEach
    void afterEach() {
        if (page != null) {
            page.close();
        }
        // Stop tracing and export it into a zip archive.
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));

        if (browserContext != null) {
            browserContext.close();
        }
    }
}
