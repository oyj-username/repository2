package cn.ithiema.springbootshizhan2.controller;


import cn.ithiema.springbootshizhan2.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {



    @RequestMapping("/index")
    public String show(){
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/LoingUrl")
    public String register(){
        return "login";
    }

    @RequestMapping("/unauth")
    @ResponseBody
    public String unshow(){

        return "error没有权限";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletResponse response, HttpServletRequest request)throws  Exception{


        //2.用shiro框架进行验证
        Subject subject = SecurityUtils.getSubject();
        //3.用户名密码令牌
        AuthenticationToken token = new UsernamePasswordToken(username,password);

        try{

            subject.login(token);
            User user =  (User) subject.getPrincipal();
            request.getSession().setAttribute("LoingUser",user);
        }catch(Exception e){
            e.printStackTrace();

            return "register";
        }

        return "index";
    }
}
