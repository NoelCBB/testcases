package resource;

import java.util.HashMap;
import java.util.Map;

public class DataRequest {
    public Map<String, String> addItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("addItem", "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 1849.99,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}");

        dataCollection.put("addItem2", "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2020,\n" + //
                        "      \"price\": 20000,\n" + //
                        "      \"CPU model\": \"Intel Core i7\",\n" + //
                        "      \"Hard disk size\": \"5 TB\"\n" + //
                        "   }\n" + //
                        "}");

        return dataCollection;
    }

    public Map<String, String> updateItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("updateItem", "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2020,\n" + //
                        "      \"price\": 15000,\n" + //
                        "      \"CPU model\": \"Intel Core i7\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}");

        dataCollection.put("updateItem2", "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2021,\n" + //
                        "      \"price\": 10000,\n" + //
                        "      \"CPU model\": \"Intel Core i6\",\n" + //
                        "      \"Hard disk size\": \"5 TB\"\n" + //
                        "   }\n" + //
                        "}");

        return dataCollection;
    }
}
