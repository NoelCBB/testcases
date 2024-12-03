package example;
import org.testng.annotations.DataProvider;

public class StaticProvider {
    @DataProvider(name = "create")
    public static Object[][] createData() {
        return new Object[][] {
        new Object[] { 42 }
    };
    
  }
  @DataProvider(name = "addObject")
    public static Object[][] createObjects() {
        return new Object[][] {
        { "Apple MacBook Pro 16", 2019, 19000, "Intel Core i9","1 TB" }
    };
  }

  @DataProvider(name = "updateObject")
    public static Object[][] updateObjects() {
        return new Object[][] {
        { "Apple MacBook Pro 17", 2020, 5000, "Intel Core i5","3 TB" }
    };
  }
}
