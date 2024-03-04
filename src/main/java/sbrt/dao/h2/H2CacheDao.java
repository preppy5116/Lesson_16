package sbrt.dao.h2;

import sbrt.dao.CacheDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class H2CacheDao extends AbstractH2CacheDao implements CacheDao {
    private final String connectionUrl;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Cache(ID INT PRIMARY KEY, ValueFib CLOB)";
    private static final String SQL_DROP_TABLE =
            "DROP TABLE Cache";

    private static final String SQL_MERGE =
            "MERGE INTO Cache KEY(ID) VALUES(?, ?);";

    private static final String SQL_SELECT =
            "SELECT ID, ValueFIb FROM Cache";


    /**
     * Если необходимо удалить полностью таблицу с кэшем, убрать комментарий
     */
    public H2CacheDao(String connectionUrl) {
        this.connectionUrl = connectionUrl;
//        dropTable();
        createTable();
    }

    /**
     * Сохраняет в базу вычисленное значение
     *
     * @param key   ключ
     * @param value вычисленное значение
     */
    @Override
    public void save(Integer key, List<Integer> value) {

        String result = value.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        try (Connection connection = getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(SQL_MERGE)) {

            statement.setInt(1, key);
            statement.setString(2, result);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't save cache", e);
        }
    }

    /**
     * Загружает из базы весь кэш
     *
     * @return Map с загруженным кэшем
     */
    @Override
    public Map<Integer, List<Integer>> load() {
        Map<Integer,List<Integer>> cache = new HashMap<>();

        try (Connection connection = getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT);


            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String value = resultSet.getString("ValueFib");

                List<Integer> numbers = new ArrayList<>();

                String[] nums = value.split(", ");
                for (String num : nums) {
                    numbers.add(Integer.parseInt(num));
                }
                cache.put(id, numbers);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't load cache", e);
        }
        return cache;
    }

    /**
     * Создает таблицу для кэша, если она отсутствует
     */
    private void createTable() {
        try (Connection connection = getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {

            statement.execute(SQL_CREATE_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException("Can't create table", e);
        }
    }

    private void dropTable() {
        try (Connection connection = getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {

            statement.execute(SQL_DROP_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException("Can't create table", e);
        }
    }
}