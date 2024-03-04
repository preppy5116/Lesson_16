package sbrt.service;

import sbrt.model.Cache;

import java.lang.reflect.Proxy;

public class CacheProxy {
    public static Calculator cache(Calculator calculator, Cache cache) {
        return (Calculator) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{Calculator.class},
                new CacheProxyHandler(calculator, cache)
        );
    }
}
