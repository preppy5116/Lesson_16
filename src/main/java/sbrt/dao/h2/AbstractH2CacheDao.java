package sbrt.dao.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractH2CacheDao {
    private static final String H2_DRIVER = "org.h2.Driver";
    public static final String H2_URL = "jdbc:h2:~/cache.db";
    private static final String H2_Login = "sa";
    private static final String H2_PASS = null;


    protected Connection getConnection(String url) {
        try {
            Class.forName(H2_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load H2 driver", e);
        }

        try {
            return DriverManager.getConnection(url, H2_Login, H2_PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection", e);
        }
    }
}
