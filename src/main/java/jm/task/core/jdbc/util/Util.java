package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Moscow";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Blecster";

    public static SessionFactory getSessionFactory(){
        return createSessionFactory();
    }

    private static SessionFactory createSessionFactory(){
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(getSettings()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder().build();

    }

    private static Map<String, String> getSettings() {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Moscow");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "Blecster");
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        return settings;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, LOGIN, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
