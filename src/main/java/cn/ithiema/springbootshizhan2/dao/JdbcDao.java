package cn.ithiema.springbootshizhan2.dao;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

@Repository
public class JdbcDao {

    @Value("${JDBC_CLASS}")
    private String jdbc_class;

    @Value("${JDBC_URL}")
    private String url;

    @Value("${JDBC_USERNAME}")
    private String username;

    @Value("${JDBC_PASSWORD}")
    private String password;

    public Connection getConnection()throws Exception{

        //1.获得连接
        Class.forName(jdbc_class);

        Connection con = DriverManager.getConnection(url,username,password);

        return con;
    }
}
