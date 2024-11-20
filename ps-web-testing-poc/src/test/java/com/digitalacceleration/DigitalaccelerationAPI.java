package com.digitalacceleration;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class DigitalaccelerationAPI {


    private APIRequestContext request;
    @Test
    public void getInventories(){
        Playwright playwright = Playwright.create();
        String baseUrl = System.getenv("inventory-ms-url");

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                // All requests we send go to this API endpoint.
                .setBaseURL(baseUrl));

        APIResponse response = request.get("/default/sgws-ms-inventory/inventories",RequestOptions.create().setIgnoreHTTPSErrors(true));

        String body = response.text();
        System.out.println(body);

    }


    @Test
    public void getInventory(){

        Playwright playwright = Playwright.create();
        String baseUrl = System.getenv("inventory-ms-url");
        String inventoryId = System.getenv("inventoryId");

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(baseUrl));

        APIResponse response = request.get("/default/sgws-ms-inventory/inventories/" + inventoryId,RequestOptions.create().setIgnoreHTTPSErrors(true));

        String body = response.text();
        System.out.println(body);

    }

    @Test
    public void getUserCart()
    {
        Playwright playwright = Playwright.create();
        String baseUrl = System.getenv("cart-ms-url");

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(baseUrl));

        APIResponse response = request.get("/cart",RequestOptions.create().setIgnoreHTTPSErrors(true));

        String body = response.text();
        System.out.println(body);


    }

    @Test
    public void addCartItem(){

        Playwright playwright = Playwright.create();
        String baseUrl = System.getenv("cart-ms-url");

        Map<String, Object> data = new HashMap<>();
        data.put("inventoryId", 587038);
        data.put("quantity", 3);


        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(baseUrl));

        APIResponse response = request.post("/cart/items",RequestOptions.create().setData(data).setIgnoreHTTPSErrors(true));
        System.out.println(response.statusText());
        String body = response.text();
        System.out.println(body);


    }

    @Test
    public void removeCartItem(){
        Playwright playwright = Playwright.create();
        String baseUrl = System.getenv("cart-ms-url");
        String itemId = System.getenv("itemId");

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(baseUrl));

        APIResponse response = request.delete("/cart/items/" + itemId,RequestOptions.create().setIgnoreHTTPSErrors(true));
        System.out.println(response.statusText());
        String body = response.text();
        System.out.println(body);


    }

    @Test
    public void cartCheckout(){

    }


}
