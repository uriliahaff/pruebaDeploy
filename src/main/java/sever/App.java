package sever;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static EntityManagerFactory entityManagerFactory;
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for (String string : env.keySet()) {
            System.out.println(string + ": " + env.get(string));
        }

        entityManagerFactory =  createEntityManagerFactory();

        Server.init();
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        String[] keys = new String[] { "hibernate.connection.url", "hibernate.connection.username",
                "hibernate.connection.password", "hibernate.connection.driver_class", "hibernate.hbm2ddl.auto",
                "hibernate.connection.pool_size", "hibernate.show_sql" };

        for (String key : keys) {
            if (env.containsKey(key)) {

                String value = env.get(key);
                configOverrides.put(key, value);

            }
        }
        return Persistence.createEntityManagerFactory("db", configOverrides);
    }

}