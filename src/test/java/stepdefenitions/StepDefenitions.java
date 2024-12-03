package stepdefenitions;

import org.json.JSONObject;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.request.AddItem;
import model.response.ResponseItem;

public class StepDefenitions {
    RequestSpecification requestSpecification;
    String idObject;
    private AddItem addItem;
    private ResponseItem responseItem;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://dummyjson.com/products";
        requestSpecification = RestAssured.given();
    }

    @Given("A list of item are available")
    public void a_list_of_item_are_available() {
        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .get();

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response: " + response.asPrettyString());
    }

    @When("I add item to list")
    public void i_add_item_to_list(String title, double width, double height, double depth) throws JsonMappingException, JsonProcessingException {
        String json = "{\n" + //
                        "   \"title\": \"Test\",\n" + //
                        "   \"dimensions\": {\n" + //
                        "      \"width\": 30.1,\n" + //
                        "      \"height\": 1849.99,\n" + //
                        "      \"depth\": \"4.3\",\n" + //
                        "   }\n" + //
                        "}";
        
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        
        //request
        addItem = objectMapper.readValue(json, AddItem.class);


        Response response = requestSpecification
                            .body(json)
                            .contentType("application/json")
                            .post();

        System.out.println("response: "+response.asPrettyString());  
        System.out.println("Hasil post: " + response.asPrettyString());

        //response
        JsonPath jsonPath = response.jsonPath();
        responseItem = jsonPath.getObject("", ResponseItem.class);

        //  Create a JSONObject from the string
        // JSONObject jsonResponse = new JSONObject(response.asString());
        
        /*
         * {
                "id": "ff808181932badb6019363648536115e",
                "name": "Apple MacBook Pro 16",
                "createdAt": "2024-11-25T12:55:52.374+00:00",
                "data": {
                    "year": 2019,
                    "CPU Model": "Intel Core i9",
                    "price": 19000,
                    "Hard disk size": "1 TB"
                }
            }
         */

        //Assertion
        Assert.assertNotNull(responseItem.id);
        Assert.assertEquals(responseItem.dimensions.width, addItem.dimensions.width);
        Assert.assertEquals(responseItem.dimensions.height, addItem.dimensions.height);
        Assert.assertEquals(responseItem.dimensions.depth, addItem.dimensions.depth);
        

        idObject = responseItem.id;
    }

    @Then("The item is available")
    public void the_item_is_available() {
        RestAssured.baseURI = "https://dummyjson.com/products" + idObject;
        RequestSpecification requestSpecification = RestAssured.given();
        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .get();

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response-deleted: " + response.asPrettyString());


        /*
         * Extract value dari API
         * [
                {
                    "id": "3",
                    "name": "Apple iPhone 12 Pro Max",
                    "data": {
                        "color": "Cloudy White",
                        "capacity GB": 512
                    }
                },
                {
                    "id": "5",
                    "name": "Samsung Galaxy Z Fold2",
                    "data": {
                        "price": 689.99,
                        "color": "Brown"
                    }
                }
            ]
            
            - Get Id, name, data
         */

         //  Create a JSONObject from the string
        // JSONObject jsonResponse = new JSONObject(response.asString());

        //Nambahin assertion


    }

    @Then("I can update that item")
    public void i_can_update_that_item() {
        RestAssured.baseURI = "https://dummyjson.com/products" + idObject;
        RequestSpecification requestSpecification = RestAssured.given();
        String json = "{\n" + //
                        "   \"title\": \"Test\",\n" + //
                        "   \"dimensions\": {\n" + //
                        "      \"width\": 30.00,\n" + //
                        "      \"height\": 1849.99,\n" + //
                        "      \"depth\": \"4.3\",\n" + //
                        "   }\n" + //
                        "}";

        Response response = requestSpecification
                            .body(json)
                            .contentType("application/json")
                            .put();
        System.out.println("Hasilnya setelah diupdate: "+ response.asPrettyString());

        //  Create a JSONObject from the string
        JSONObject jsonResponse = new JSONObject(response.asString());

        //Assertion
        Assert.assertNotNull(jsonResponse.getString("id"));
        Assert.assertEquals(jsonResponse.getJSONObject("dimensions").getDouble("width"), 30.00);
        Assert.assertEquals(jsonResponse.getJSONObject("dimensions").getDouble("height"), 1849.99);
        Assert.assertEquals(jsonResponse.getJSONObject("dimensions").getDouble("depth"), 4.3);
    }

    @When("I delete that item")
    public void i_delete_that_item() {
         /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .pathParams("id", idObject)
                            .contentType("application/json")
                            .delete("{id}");

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response: " + response.asPrettyString());

        //  Create a JSONObject from the string
        // JSONObject jsonResponse = new JSONObject(response.asString());

        //Do assertion
        Assert.assertEquals(statusCode, 200);
        // Assert.assertEquals("Object with id = "+idObject+" has been deleted.", jsonResponse.getString("message"));
    }

    @Then("The item isn't available")
    public void the_item_isn_t_available() {
        RestAssured.baseURI = "hhttps://dummyjson.com/products" + idObject;
        RequestSpecification requestSpecification = RestAssured.given();
        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .get();

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response-deleted: " + response.asPrettyString());


        /*
         * Extract value dari API
         * [
                {
                    "id": "3",
                    "name": "Apple iPhone 12 Pro Max",
                    "data": {
                        "color": "Cloudy White",
                        "capacity GB": 512
                    }
                },
                {
                    "id": "5",
                    "name": "Samsung Galaxy Z Fold2",
                    "data": {
                        "price": 689.99,
                        "color": "Brown"
                    }
                }
            ]
            
            - Get Id, name, data
         */

         //  Create a JSONObject from the string
        // JSONObject jsonResponse = new JSONObject(response.asString());

        //Do assertion
        Assert.assertEquals(statusCode, 404);
        // Assert.assertEquals("Oject with id="+idObject+" was not found.", jsonResponse.getString("error"));
    }
}
