package com.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author IceCube
 * @date 2020/7/23 17:22
 */
public class DAOUtil {
    public static final String configPath = "mybatis-config.xml";
    public static SqlSessionFactory factory;

    static {
        try (InputStream in = Resources.getResourceAsStream(configPath)) {
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return factory;
    }
}
