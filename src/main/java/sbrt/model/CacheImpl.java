package sbrt.model;

import sbrt.dao.CacheDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheImpl implements Cache {
    private Map<Integer, List<Integer>> cache = new HashMap<>();
    private final CacheDao cacheDao;

    public CacheImpl(CacheDao cacheDao) {
        this.cacheDao = cacheDao;
    }

    @Override
    public void put(Integer key, List<Integer> value, boolean persistent) {
        cache.put(key, value);
        if (persistent) cacheDao.save(key, value); // Сохраняем в базу, если требуется
    }

    @Override
    public List<Integer> get(Integer key) {
        return cache.get(key);
    }

    @Override
    public void load() {
        cache = cacheDao.load();
    }
}

