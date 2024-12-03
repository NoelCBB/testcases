package scenario;

import java.util.List;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.response.ResponseItem;

public class RestAssuredTest2 {


    // Deserialize
    @Test
    public void GetAllObjectsByIds(){
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        RequestSpecification requestSpecification = RestAssured.given();

        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .queryParam("id", 3)
                            .queryParam("id", 5)
                            .queryParam("id",10)
                            .get();

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response: " + response.asPrettyString());


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
        JsonPath jsonPath = response.jsonPath();

        //Extract Value Response
        System.out.println("Id : " + jsonPath.get("id"));
        System.out.println("Name : " + jsonPath.get("name"));
        System.out.println("Data : " + jsonPath.get("data"));

        //Deserialize JSON to Object 
        List<ResponseItem> gItems = jsonPath.getList("", ResponseItem.class);
        System.out.println("Result Item" + gItems);

        // for (ResponseItem item : gItems) {
        //     System.out.println("===============");
        //     System.out.println(item.id);
        //     System.out.println(item.name);
        //     System.out.println(item.data.price);
        //     System.out.println("===============");
        // }
    }
}
