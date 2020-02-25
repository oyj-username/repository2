package cn.ithiema.springbootshizhan2.config;


import cn.ithiema.springbootshizhan2.controller.BosRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Qualifier;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //1.shiro

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean( DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String,String> map = new HashMap<String,String>();
        map.put("/index","authc");
       /* map.put("/register","authc");*/

        //授权(访问路径/uesr/add，必须具有，后面的权限,注意，用户必然需要已经认证过了，才能确认是否授权)
        map.put("/hello","perms[user:add]");

        bean.setFilterChainDefinitionMap(map);

        //作用是，如果因为没有认证，而被拦截就自动访问下面路径，该路径下一般都是登录页面
        bean.setLoginUrl("/LoingUrl");
        //如果访问该页面，没有要求的权限，就会执行该路径
        bean.setUnauthorizedUrl("/unauth");

        return bean;
    }

    //2.bean
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(BosRealm bosRealm ){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(bosRealm);
        return securityManager;
    }


    //3.BosRealm
    @Bean
    public BosRealm bosRealm(){

        return new BosRealm();
    }
}
