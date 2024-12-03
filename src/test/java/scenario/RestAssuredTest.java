package scenario;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import example.StaticProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredTest {
    RequestSpecification requestSpecification;
    String idObject;
    String token;

    /*
     * Materi kita kali ini akan combine restassured dan testng
     * Kita akan menggunakan scenario dari API "https://restful-api.dev"
     * Scenario test nya adalah:
     * 1. User dapat melihat list of objects
     * 2. User ingin menambahkan data baru
     * 3. Setelah datanya sudah berhasil di insert, user ingin update datanya
     * 4. Diakhir user ingin delete data yang baru saja dia add
     * 5. Makesure user tidak bisa lagi akses datanya, karena datanya sudah berhasil terhapus
     */

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://dummyjson.com/products";
        requestSpecification = RestAssured.given();
    }

    // User dapat melihat list of objects
    @Test
    public void GetAllObjects(){
        /*
         * Get response dari request 
         */
        Response response = requestSpecification
                            .header("Authorization", "Bearer" + token)
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

    // User ingin menambahkan data baru
    @Test(dataProvider = "addObject", dataProviderClass = StaticProvider.class)
    public void AddObject(String name, int year, double price, String cpuModel, String hardDiskSize){
        // String json = "{\n" + //
        //                 "   \"name\": \"Apple MacBook Pro 16\",\n" + //
        //                 "   \"data\": {\n" + //
        //                 "      \"year\": 2019,\n" + //
        //                 "      \"price\": 1849.99,\n" + //
        //                 "      \"CPU model\": \"Intel Core i9\",\n" + //
        //                 "      \"Hard disk size\": \"1 TB\"\n" + //
        //                 "   }\n" + //
        //                 "}";


        /*
        Kita punya 2 object
        1. Object Item
        2. Object data
         */
        
        JSONObject jsonObject =  new JSONObject();
        JSONObject jsonData = new JSONObject();

        jsonObject.put("name", name);
        jsonData.put("year", year);
        jsonData.put("price", price);
        jsonData.put("CPU Model", cpuModel);
        jsonData.put("Hard disk size", hardDiskSize);

        jsonObject.put("data", jsonData);

        Response response = requestSpecification
                            .body(jsonObject.toString())
                            .contentType("application/json")
                            .post();
        System.out.println("albert"+response.asPrettyString());  
        System.out.println("Hasil post : " + response.asPrettyString());

        //  Create a JSONObject from the string
        JSONObject jsonResponse = new JSONObject(response.asString());
        
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
        Assert.assertNotNull(jsonResponse.getString("id"));
        Assert.assertEquals(jsonResponse.getJSONObject("data").getInt("year"), 2019);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getString("CPU Model"), cpuModel);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getDouble("price"), price);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getString("Hard disk size"), hardDiskSize);

        idObject = jsonResponse.getString("id");
    }

    //Setelah datanya sudah berhasil di insert, user ingin update datanya
    @Test(dataProvider = "updateObject", dataProviderClass = StaticProvider.class,dependsOnMethods = {"AddObject"}, priority = 1)
    public void UpdateObject(String name, int year, double price, String cpuModel, String hardDiskSize){
        RestAssured.baseURI = "https://dummyjson.com/products/" + idObject;
        RequestSpecification requestSpecification = RestAssured.given();
        // String json = "{\n" + //
        //                 "   \"name\": \"Apple MacBook Pro 16\",\n" + //
        //                 "   \"data\": {\n" + //
        //                 "      \"year\": 2019,\n" + //
        //                 "      \"price\": 2049.99,\n" + //
        //                 "      \"CPU model\": \"Intel Core i9\",\n" + //
        //                 "      \"Hard disk size\": \"1 TB\",\n" + //
        //                 "      \"color\": \"silver\"\n" + //
        //                 "   }\n" + //
        //                 "}";

        JSONObject jsonObject =  new JSONObject();
        JSONObject jsonData = new JSONObject();

        jsonObject.put("name", name);
        jsonData.put("year", year);
        jsonData.put("price", price);
        jsonData.put("CPU Model", cpuModel);
        jsonData.put("Hard disk size", hardDiskSize);

        jsonObject.put("data", jsonData);

        Response response = requestSpecification
                            .body(jsonObject.toString())
                            .contentType("application/json")
                            .put();
        System.out.println("Hasilnya albert - updated : "+ response.asPrettyString());

        //  Create a JSONObject from the string
        JSONObject jsonResponse = new JSONObject(response.asString());

        //Assertion
        Assert.assertNotNull(jsonResponse.getString("id"));
        Assert.assertEquals(jsonResponse.getJSONObject("data").getInt("year"), year);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getString("CPU Model"), cpuModel);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getDouble("price"), price);
        Assert.assertEquals(jsonResponse.getJSONObject("data").getString("Hard disk size"), hardDiskSize);
    }

    @Test(dependsOnMethods = {"AddObject"}, priority = 2)
    public void DeleteDataObject(){
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
        JSONObject jsonResponse = new JSONObject(response.asString());

        //Do assertion
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Object with id = "+idObject+" has been deleted.", jsonResponse.getString("message"));
    }

    //Makesure user tidak bisa lagi akses datanya, karena datanya sudah berhasil terhapus
    @Test(dependsOnMethods = {"DeleteDataObject"}, priority = 3)
    public void GetAllObjectsByIds(){
        RestAssured.baseURI = "https://dummyjson.com/products/" + idObject;
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
        JSONObject jsonResponse = new JSONObject(response.asString());

        //Do assertion
        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals("Oject with id="+idObject+" was not found.", jsonResponse.getString("error"));
    }

}
