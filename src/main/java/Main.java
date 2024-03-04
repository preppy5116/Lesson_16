import sbrt.dao.h2.AbstractH2CacheDao;
import sbrt.dao.h2.H2CacheDao;
import sbrt.model.Cache;
import sbrt.model.CacheImpl;
import sbrt.service.CacheProxy;
import sbrt.service.Calculator;
import sbrt.service.CalculatorImpl;


public class Main {
    public static void main(String[] args) {
        Cache cache = new CacheImpl(new H2CacheDao(AbstractH2CacheDao.H2_URL));
        cache.load();

        Calculator calculator = CacheProxy.cache(new CalculatorImpl(), cache);

        long start = System.nanoTime();
        System.out.println("Fib 10: " + calculator.fibonacci(10));
        System.out.println("Fib 15: " + calculator.fibonacci(15));
        System.out.println("Fib 20: " + calculator.fibonacci(20));
        System.out.println("Fib 25: " + calculator.fibonacci(25));
        System.out.println("Fib 30: " + calculator.fibonacci(30));
        System.out.println("Fib 35: " + calculator.fibonacci(35));
        System.out.println("Fib 40: " + calculator.fibonacci(40));
        System.out.println("Fib 45: " + calculator.fibonacci(45));
        System.out.println("Fib 46: " + calculator.fibonacci(46));

        System.out.println("\nExecution time (ns) " + (System.nanoTime() - start));

    }
}

