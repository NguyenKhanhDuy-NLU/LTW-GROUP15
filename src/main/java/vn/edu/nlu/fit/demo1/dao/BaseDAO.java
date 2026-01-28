package vn.edu.nlu.fit.demo1.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class BaseDAO {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties props = new Properties();

            InputStream input = BaseDAO.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("Không thể tìm thấy db.properties");
            }

            props.load(input);

            String driver = props.getProperty("db.driver");
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");

            Class.forName(driver);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi cấu hình database", e);
        }
    }

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Không thể kết nối với database", e);
        }
    }
}


