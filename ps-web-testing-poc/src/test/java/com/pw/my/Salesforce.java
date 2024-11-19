package com.pw.my;

import com.microsoft.playwright.*;
import com.microsoft.playwright.impl.FormDataImpl;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import com.pw.ScriptBase;
import groovy.json.JsonSlurper;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import java.util.*;


public class Salesforce extends ScriptBase{

    static String accessToken;
    static String accountId;

    @Test
    public void proposals() {

        page.navigate("https://sgwsteam--proofqa.sandbox.my.salesforce.com");

        page.fill("id=username", "magen.kroutter@sgws.com.proofqa");
        page.fill("id=password", "Welcome$25");
        page.click("#Login");

        page.waitForTimeout(5000); // waits for 5 seconds

        Assertions.assertTrue(page.title().contains("Lightning Experience"));
        page.navigate("https://sgwsteam--proofqa.sandbox.lightning.force.com/lightning/o/Proposal__c/list?filterName=__Recent");

        page.click("text=New");

        String radioButtonXpath = "//*[@id=\"modal-content-id-1\"]/lightning-radio-group/fieldset/div/span[1]/label/span[1]";
        page.locator(radioButtonXpath).click();

        page.waitForTimeout(5000); // waits for 5 seconds
        page.getByTitle("Next").click();

        page.getByLabel("*Proposal Name").fill("Proposal Test 001");
        page.getByPlaceholder("search..").fill("AJS OF GRAYTON BEACH");
        page.keyboard().press("Enter");
        page.locator("id=listbox-option-unique-id-01").click();
        page.getByTitle("Save").click();
        page.click("text=Add Products");
        page.waitForTimeout(5000);

        page.locator("[name='SearchKey']").fill("451040"); //917408
        page.keyboard().press("Enter");
      page.waitForTimeout(5000);


        page.locator("id=brandBand_2").click();

        String productCheckBoxXpath = "//*[@id=\"brandBand_2\"]/div/div/div[1]/div/app_flexipage-lwc-app-flexipage/app_flexipage-lwc-app-flexipage-decorator/slot/app_flexipage-lwc-app-flexipage-internal/forcegenerated-adg-rollup_component___force-generated__flexipage_-app-page___-site_-product_-search/forcegenerated-flexipage_site_product_search__js/flexipage-lwc-default-app-home-template/div/div/slot/flexipage-component2[3]/slot/flexipage-aura-wrapper/div/div/div[3]/div[2]/div/div/table/tbody/tr/td[1]/div/lightning-input/lightning-primitive-input-checkbox/div/span/label/span[1]";
        page.locator(productCheckBoxXpath).hover();
        page.locator(productCheckBoxXpath).click();

        page.getByTitle("Add Selected Products").click();
        page.waitForTimeout(5000);
        page.locator("li").filter(new Locator.FilterOptions().setHasText("View Proposal")).getByRole(AriaRole.BUTTON).click();
        // Validate
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Validate")).click();
        page.waitForTimeout(5000);
        // Price
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.waitForTimeout(5000);
        //PDF
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("PDF")).click();
        page.waitForTimeout(5000);

        page.waitForTimeout(5000); // waits for 5 seconds
        page.getByTitle("Ok").click();

        page.waitForTimeout(15000); // waits for 15 seconds

    }

    @Test
    public void getAccessToken() {

        String endPoint = System.getenv("endPoint");
        String resource = System.getenv("resource");
        String url = endPoint + resource;
        String refresh_token = System.getenv("refresh_token");
        String client_id = System.getenv("client_id");
        String client_secret = System.getenv("client_secret");
        String access_token = "";

        APIRequest request = playwright.request();
        FormData form = new FormDataImpl();

            form.set("grant_type", "refresh_token");
            form.set("client_id", client_id);
            form.set("client_secret", client_secret);
            form.set("refresh_token", refresh_token);

            APIResponse response = request.newContext().post(url, RequestOptions.create().setForm(form));
            if (response.ok()) {
                try {
                    String body = response.text();
                    JSONObject jsonObject = new JSONObject(body);
                    accessToken = jsonObject.getString("access_token");



                    //access_token = objJSL.access_token;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("Error refreshing access token: "  + response.statusText() + " " + response.status());
            }
        System.out.println("Access Token : " + accessToken);
    }

    @Test
    public void getAccount() {

        getAccessToken();

        String url = System.getenv("endPoint") + "/services/data/v55.0/query/?q";
        APIRequest request = playwright.request();
        // Set headers for the request
        APIResponse response = request.newContext().get(url, RequestOptions.create()
                .setHeader("Authorization", "Bearer " + accessToken)
                .setHeader("Content-Type", "application/json")
                .setQueryParam("q","SELECT Id,AccountNumber,Name,Account_Name_and_State__c,SWS_External_ID__c,SGWS_Site_Active_on_eComm__c,SGWS_eComm_excluded__c,SGWS_eComm_Enabled__c,SWS_Account_Site__c,SWS_Site_Ext_ID__c FROM Account WHERE Name='NEIGHBORHOOD GROCERY' AND AccountNumber='30069'")
        );

        String body = response.text();
        System.out.println(body);

        JSONObject jsonObject = new JSONObject(body);

        String contactId = "";
        try {
            JSONArray records = jsonObject.getJSONArray("records");
            JSONObject arrayElement = records.getJSONObject(0);
            contactId = arrayElement.getString("Id");

            String cid = jsonObject.getJSONArray("records").getJSONObject(0).getString("Id");

            System.out.println("contactId" + cid);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//
//        request = playwright.request().newContext(new APIRequest.NewContextOptions()
//                .setBaseURL(BASE_URL)
//                .setExtraHTTPHeaders(headers));

        System.out.println("Status Code : "  + response.statusText() + " " + response.status());

    }



}





