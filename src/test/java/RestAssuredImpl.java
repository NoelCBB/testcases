import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredImpl {
    public static void main(String[] args) {
        // GetAllObjects();
        GetAllObjectsByIds();
        AddObject();
        UpdateObject();
    }

    @Test
    public void GetAllObjects(){
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        RequestSpecification requestSpecification = RestAssured.given();

        /*
         * Get response dari request 
         */
        Response response = requestSpecification.get();

        /*
         * Get response dari request
         * - Status code
         * - Response body
         */
        int statusCode = response.getStatusCode();
        System.out.println("Statuscode : " + statusCode);
        System.out.println("Response: " + response.asPrettyString());
    }

    public static void GetAllObjectsByIds(){
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        RequestSpecification requestSpecification = RestAssured.given();

        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .queryParam("id", 3)
                            .queryParam("id", 5)
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
    }

    public static void AddObject(){
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        RequestSpecification requestSpecification = RestAssured.given();
        String json = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 1849.99,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}";
        Response response = requestSpecification
                            .body(json)
                            .contentType("application/json")
                            .post();
        System.out.println(response.asPrettyString());  
        System.out.println("Hasil post : " + response.asPrettyString());      
    }

    public static void UpdateObject(){
        String idObject = "ff80818192925da70192f74017f95aee";
        RestAssured.baseURI = "https://api.restful-api.dev/objects/" + idObject;
        RequestSpecification requestSpecification = RestAssured.given();
        String json = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 2049.99,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\",\n" + //
                        "      \"color\": \"silver\"\n" + //
                        "   }\n" + //
                        "}";
        Response response = requestSpecification
                            .body(json)
                            .contentType("application/json")
                            .put();
        System.out.println("Hasilnya : "+ response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();

        //Extract Value Response
        System.out.println("Id : " + jsonPath.get("id"));
        System.out.println("Name : " + jsonPath.get("name"));
        System.out.println("Data : " + jsonPath.get("data"));
        System.out.println("UpdatedAt : " + jsonPath.get("updatedAt"));
    }
}
