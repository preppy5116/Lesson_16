package sbrt.service;

import sbrt.model.Cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unchecked")
public class CacheProxyHandler  implements InvocationHandler {
    private final Object delegate;
    private final Cache cache;

    public CacheProxyHandler(Object delegate, Cache cache) {
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.isAnnotationPresent(Cachable.class)) {

            Cachable an = method.getAnnotation(Cachable.class);
            boolean persistent = an.persistent();

            Integer key = (Integer) args[0];
            List<Integer> cachedResult = cache.get(key);

            if (cachedResult != null) {
                System.out.print("Get value from cache ");
                return cachedResult;
            }
            System.out.print("Calculate value ");
            List<Integer> result = (List<Integer>) method.invoke(delegate, args);

            cache.put(key, result, persistent);
            return result;
        }

        return method.invoke(delegate, args);
    }
}
