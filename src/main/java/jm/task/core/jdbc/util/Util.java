package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
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
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        settings.put(Environment.URL, HOST);
        settings.put(Environment.USER, LOGIN);
        settings.put(Environment.PASS, PASSWORD);
        registryBuilder.applySettings(settings);
        StandardServiceRegistry registry= registryBuilder.build();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();
        MetadataSources metadataSources = new MetadataSources(registry)
                .addAnnotatedClass(User.class);
        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        
        return sessionFactory;
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
