package com.pw.m2;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BrowserSupportDemo {

    @Test
    public void browserSupport() {
        try(Playwright pw = Playwright.create()){

            //List<BrowserType> browserTypes = List.of(pw.chromium(), pw.firefox(), pw.webkit());

            List<BrowserType> browserTypes = new ArrayList();
            browserTypes.add(pw.chromium());
            browserTypes.add(pw.firefox());
            browserTypes.add(pw.webkit());

            for(BrowserType type : browserTypes) {
                Page page = type.launch().newPage();
                page.navigate("https://www.whatsmybrowser.org/");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(type.name() + ".png")));
            }
        }
    }
}
