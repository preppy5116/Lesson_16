package sbrt.service;

import sbrt.service.Calculator;

import java.util.List;
import java.util.ArrayList;


public class CalculatorImpl implements Calculator {
    @Override
    public List<Integer> fibonacci(int n) {
        List<Integer> result = new ArrayList<>();
        if (n < 1 || n > 46) throw new IllegalArgumentException("1 <= n <= 46");
        if (n == 1) {
            result.add(0);
        } else if (n >= 2) {
            result.add(0);
            result.add(1);
            for (int i = 2; i < n; i++) {
                result.add(result.get(i - 1) + result.get(i - 2));
            }
        }
        return result;
    }
}
