package com.example.mobile.base.config;

import com.example.mobile.base.constant.JwtConstant;
import com.example.mobile.base.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
@Slf4j
@AutoConfigurationPackage
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value(value = "${spring.redis.port}")
    private int port;
    @Value(value = "${spring.redis.timeout}")
    private int timeout;
//    @Value(value = "${spring.redis.password}")
//    private String password;
    @Value(value = "${spring.redis.expire}")
    private int expire;

    /**
     * 拦截器链（用于注册自定义的拦截器）
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")//此处一定要设置name=shiroFilterFactoryBean 否则会报错：Consider defining a bean named 'shiroFilterFactoryBean' in your configuration.
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean  shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //登录成功后跳转的路径
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setFilters(filterMap());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitionMap());//导入自定义的拦截规则
        return shiroFilterFactoryBean;
    }

    /**
     * 配置securityManager 管理subject（默认）,并把自定义realm交由manager
     * 注意：不能使用new 来创建对象，要使用@Bean注入的方式，否则会出现自定义的Realm为NULL
     * @return
     */
    @Bean(name = "securityManager")// 容器中自动配置了SecurityManager  所以我们使用SessionSecurityManager 覆盖
    public SessionsSecurityManager securityManager()
    {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //自定义Realm
        defaultWebSecurityManager.setRealm(getRealm());
        //自定义缓存 使用redis
        defaultWebSecurityManager.setCacheManager(cacheManager());
        //自定义session管理 使用redis
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }


    /**
     * url拦截规则
     * 注意：Map中key和value类型必须为String
     * @return
     */
    public Map<String,String> definitionMap()
    {
        Map<String,String> definitionMap = new HashMap<>();
        //游客，开发权限
//        definitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
//        filterMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
//        definitionMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        definitionMap.put("/login", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
//        definitionMap.put("/**", "authc");
        definitionMap.put("/**", JwtConstant.JWT_FILTER_NAME);
        return definitionMap;
    }

    /**
     * 自定义拦截器，处理所有请求
     */
    private Map<String, Filter> filterMap() {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(JwtConstant.JWT_FILTER_NAME, new JwtFilter());
        return filterMap;
    }

    /**
     * 开启注解
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib代理，防止和aop冲突
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自定义身份认证Realm（包含用户名密码校验，权限校验等）
     * @return
     */
    @Bean
    public MyRealm getRealm()
    {
        return new MyRealm();
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
     public RedisCacheManager cacheManager()
     {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
     }

     public RedisManager redisManager()
     {
         RedisManager redisManager = new RedisManager();
         redisManager.setHost(new RedisConfigEntity().getHost());
         redisManager.setPort(port);
         redisManager.setTimeout(timeout);
//         redisManager.setPassword();
         redisManager.setExpire(expire);
         return redisManager;
     }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     * @return
     */
     @Bean
     public DefaultWebSessionManager sessionManager()
     {
         DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
         defaultWebSessionManager.setSessionDAO(redisSessionDAO());
         return  defaultWebSessionManager;
     }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }





}
