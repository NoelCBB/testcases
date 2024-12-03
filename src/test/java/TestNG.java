import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import example.ExecutionListener;
import example.StaticProvider;

@Listeners(ExecutionListener.class)
public class TestNG {
    
    //Basic 1 annotation test
    @Test
    public void scenarioTest1(){
        System.out.println("Ini adalah scenario test 1");
    }

    @Test
    public void scenarioTest2(){
        System.out.println("Ini adalah scenario test 2");
    }

    //Annotation before
    @BeforeClass
    public void setUpClass(){
        System.out.println("Ini akan dijalankan sekali ketika setup class");
    }

    @BeforeMethod
    public void setUpMethod(){
        System.out.println("Ini akan dijalankan setiap scenario test dipanggil");
    }

    //Annotation after
    @AfterClass
    public void afterClass(){
        System.out.println("Ini akan dijalankan setelah semua scenario dijalankan pada level class");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("Ini akan dijalankan setelah setiap scenario/method dijalankan");
    }

    //Grouping
    @Test(groups = {"group1"}) 
    public void scenarioTestGroup1(){
        System.out.println("Ini adalah scenario test group1");
    }

    @Test(groups = {"group2"}) 
    public void scenarioTestGroup2(){
        System.out.println("Ini adalah scenario test group2");
    }

    //Parameters
    @Parameters({"program"})
    @Test
    public void scenarioTestParams(String program){
        System.out.println("Nama programnya adalah : " + program);
        Assert.assertEquals(program, "Bootcamp API Automation");
    }

    /*
        Kalau kita pengen combine dataprovider dengan parameter
        Karena mungkin saja datanya complex
    */

    @DataProvider(name = "test1")
    public Object[][] personData(){
        return new Object[][]{
            {"Albert", 25},
            {"Jeffry", 26}
        };
    }
    

    @Test(dataProvider =  "test1")
    public void scenarioTestDataProvider(String name, int age){
        System.out.println("Data collection : " + name + age);
    }

    @Test(dataProvider = "create", dataProviderClass = StaticProvider.class)
    public void test(Integer n) {
        System.out.println("Hasilnya adalah: " + n);
    }

    String sentence;

    @Test
    public void dependMethod(){
        /*
         * Pertama kita define sentence terlebih dahulu ini akan menjadi inisiasi untuk method yang memiliki dependency kesini
         */

         sentence = "Ini adalah class advance";
    }

    @Test(dependsOnMethods = {"dependMethod"})
    public void dependScenarioTest(){
        /*
         * Kemudian kita akan printout hasilnya
         */

         System.out.println("Hasil dari get value dependency: " + sentence);
    }
}
