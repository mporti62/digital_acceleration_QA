package com.pw.m5;

import com.microsoft.playwright.Page;
import com.pw.ScriptBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClickFillOptions extends ScriptBase{

    @Test
    public void clickOptionsCount() {

        page.navigate(home);
        page.click("#clap-image", new Page.ClickOptions().setClickCount(2));

        Assertions.assertTrue(page.isVisible("#thank-you-2"));
        // OR
        Assertions.assertTrue(page.isVisible("text=You can only clap once, but thanks for your enthusiasm"));
    }

}
