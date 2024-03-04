package sbrt.service;

import java.util.List;

public interface Calculator {
    @Cachable(persistent = true)
    List<Integer> fibonacci(int n);
}
