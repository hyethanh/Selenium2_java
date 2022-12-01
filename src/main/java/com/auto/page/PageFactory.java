package com.auto.page;


import com.auto.page.imp.browser.*;
import com.auto.utils.Constants;
import com.logigear.statics.Selaium;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class PageFactory {
    private static final Logger logger = LoggerFactory.getLogger(PageFactory.class);
    private static final Map<String, Map<String, Class<?>>> pages = new HashMap<>();

    static {

        // Login page implementation
        pages.put(Page.Login, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, LoginPage.class);
        }});

        // Home page implementation
        pages.put(Page.Home, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, HomePage.class);
        }});

        pages.put(Page.Dialog, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, DialogPage.class);
        }});

        pages.put(Page.Panel, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, PanelPage.class);
        }});

        pages.put(Page.Form, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, FormPage.class);
        }});

        pages.put(Page.DataProfiles, new HashMap<String, Class<?>>() {{
            put(Constants.BROWSER, DataProfilePage.class);
        }});
    }

    @SuppressWarnings("unchecked")
    private static <T> T get(String pageName) {
        String platform = Selaium.config().getPlatform().toLowerCase();
        if (StringUtils.isBlank(platform)) {
            throw new IllegalArgumentException("Platform must be configure");
        }
        Class<?> clazz = pages.get(pageName).get(platform);
        if (clazz == null) {
            throw new IllegalArgumentException(String.format("%s for %s is not implemented", pageName, platform));
        }
        try {
            return (T) clazz.newInstance();
        } catch (Exception exception) {
            logger.error("Cannot initial page object", exception);
        }
        throw new RuntimeException("Cannot initial page object for " + pageName);
    }

    public static ILoginPage getLoginPage() {
        return get(Page.Login);
    }


    public static IHomePage getHomePage() {
        return get(Page.Home);
    }

    public static IDialogPage getDialogPage() {
        return get(Page.Dialog);
    }

    public static IPanelPage getPanelPage() {
        return get(Page.Panel);
    }

    public static IFormPage getFormPage() {
        return get(Page.Form);
    }

    public static IDataProfilePage getDataProfilePage() {
        return get(Page.DataProfiles);
    }


    private static class Page {
        private static final String Login = "LoginPage";
        private static final String Home = "HomePage";
        private static final String Dialog = "DialogPage";
        private static final String Panel = "PanelPage";
        private static final String Form = "FormPage";
        private static final String DataProfiles = "DataProfilePage";
    }
}
