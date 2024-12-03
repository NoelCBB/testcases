package apiengine;

import com.apiautomationadvance.constants.Constants;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    public static RequestSpecification requestSpecification;

    public Endpoints(){
        RestAssured.baseURI = Constants.BASE_URL+"/objects";
        requestSpecification = RestAssured.given();
    }

    public Response getAllItems(){      
        Response response = requestSpecification
                            .get();
        return response;
    }

    public Response addItem(String json){
        Response response = requestSpecification
                            .body(json)
                            .contentType("application/json")
                            .post();
        return response;
    }

    public Response getSpesificItem(String idObject){
        Response response = requestSpecification
                            .pathParams("id", idObject)
                            .contentType("application/json")
                            .get("{id}");
        
        return response;
    }

    public Response updateItem(String payload, String idObject){
        Response response = requestSpecification
                            .body(payload)
                            .pathParams("id", idObject)
                            .contentType("application/json")
                            .put("{id}");
        return response;
    }

    public Response deleteItem(String idObject){
        Response response= requestSpecification
                            .pathParams("id", idObject)
                            .contentType("application/json")
                            .delete("{id}");
        return response;
    }
}
