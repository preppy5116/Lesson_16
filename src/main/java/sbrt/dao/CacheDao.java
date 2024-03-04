package sbrt.dao;

import java.util.List;
import java.util.Map;

public interface CacheDao {
    void save(Integer key, List<Integer> value);
    Map<Integer, List<Integer>> load();
}
