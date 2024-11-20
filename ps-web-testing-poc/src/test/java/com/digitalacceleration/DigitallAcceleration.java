package com.digitalacceleration;

import com.microsoft.playwright.Locator;
import com.pw.ScriptBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DigitallAcceleration extends ScriptBase {
        @Test
         public void Login() {

            page.navigate(login);

            page.fill("id=username", "johndoe@gmail.com");
            page.fill("id=password", "digitalacc01");
            page.click("id =submit");

            page.waitForTimeout(5000); // waits for 5 seconds

            //Assertions.assertTrue(page.title().contains("DigitalAcceleration"));

        }

        public void ProductList(String _productId){

            //Loop table and select the product
            //The products selected shoud be added to the cart
            // Get all elements matching the locator
            Locator elements = page.locator("your_locator_here");
            // Loop through the elements
            for (int i = 0; i < elements.count(); i++) {
                Locator element = elements.nth(i);
                if(elements.nth(i).textContent().equals(_productId)) {
                    //click add button
                }
            }
        }

        public void EmtyCart(){
             // Loop product list and remove from cart
            Locator elements = page.locator("your_locator_here");
            // Loop through the elements
            for (int i = 0; i < elements.count(); i++) {
                Locator element = elements.nth(i);
                // click remove product button
            }
         }



        public void AddToCart(){

            // Select Products and add to the cart
            ProductList("productId");
        }

    }

