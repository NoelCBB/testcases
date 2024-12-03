package apiengine;

import java.util.List;

import org.testng.Assert;

import model.request.AddItem;
import model.response.ResponseItem;

public class Assertion {
    public void assertAddItem(ResponseItem responseItem, AddItem addItem){
        Assert.assertNotNull(responseItem.id);
        Assert.assertEquals(responseItem.dimensions.width, addItem.dimensions.width);
        Assert.assertEquals(responseItem.dimensions.height, addItem.dimensions.height);
        Assert.assertEquals(responseItem.dimensions.depth, addItem.dimensions.depth);

    }

    public void assertAvailableItem(List<ResponseItem> gItems, AddItem request, String idObject){
        for (ResponseItem item : gItems) {
            System.out.println("===============");
            if (item.id == idObject) {
                Assert.assertNotNull("id");
                Assert.assertEquals(item.dimensions.width, request.dimensions.width);
                Assert.assertEquals(item.dimensions.height, request.dimensions.height);
                Assert.assertEquals(item.dimensions.depth, request.dimensions.depth);
            }
            System.out.println("===============");
        }
    }
}
