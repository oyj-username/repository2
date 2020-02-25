package cn.ithiema.springbootshizhan2.controller;


import cn.ithiema.springbootshizhan2.bean.User;
import cn.ithiema.springbootshizhan2.dao.JdbcDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BosRealm  extends AuthorizingRealm {


    @Autowired
    private JdbcDao jdbcDao;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken mytoken = (UsernamePasswordToken) token;

        String username = mytoken.getUsername();

        Connection con = null;
        List<User> list = new ArrayList<User>();
        //1.根据用户名查询,数据库中的信息；
        try{

            con = jdbcDao.getConnection();
            //获得连接着对象
            String sql = "select * from tb_user where username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setObject(1,username);

            //获得结果集
            ResultSet rs = pst.executeQuery();

            //6.取出结果集

            while(rs.next()){
                list.add(new User(rs.getString("username"),rs.getString("password")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        User user = list.get(0);
        System.out.println("hello world");
        System.out.println(user.getPassword());
        if(user == null){
            //用户名不存在
            return null;
        }

        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;


    }

    //权限控制方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
