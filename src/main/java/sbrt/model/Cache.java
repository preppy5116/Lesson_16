package sbrt.model;

import java.util.List;

public interface Cache {
    void put(Integer key, List<Integer> value, boolean persistent);
    List<Integer> get(Integer key);
    void load();
}
