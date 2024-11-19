package com.digitalacceleration;

import com.pw.ScriptBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DigitallAcceleration extends ScriptBase {
        @Test
        public void Login() {

            page.navigate(login);

            page.fill("id=username", "johndoe@gmail.com");
            page.fill("id=password", "digitalacc01");
            page.click("#Login");

            page.waitForTimeout(5000); // waits for 5 seconds

            Assertions.assertTrue(page.title().contains("DigitalAcceleration"));


        }
    }

