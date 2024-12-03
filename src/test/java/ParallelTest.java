import org.testng.annotations.Test;

public class ParallelTest {
    @Test
    public void testMethod1(){
        System.out.println("Ini adalah test method1 di thread: " + Thread.currentThread().getThreadGroup());
    }

    @Test
    public void testMethod2(){
        System.out.println("Ini adalah test method2 di thread: " + Thread.currentThread().getThreadGroup());
    }

    @Test
    public void testMethod3(){
        System.out.println("Ini adalah test method3 di thread: " + Thread.currentThread().getThreadGroup());
    }

    @Test(retryAnalyzer = RetryTest.class)
    public void testRetry(){
        System.out.println("Execute test ini");
        assert false;
    }
}
