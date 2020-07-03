package com.example.mobile.base.config;

import com.example.mobile.base.filter.JwtFilter;
import com.example.mobile.constance.JwtConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * 拦截器链（用于注册自定义的拦截器）
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
//此处一定要设置name=shiroFilterFactoryBean 否则会报错：Consider defining a bean named 'shiroFilterFactoryBean' in your configuration.
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
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
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //自定义Realm
        defaultWebSecurityManager.setRealm(getRealm());
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }


    /**
     * url拦截规则
     * 注意：Map中key和value类型必须为String
     * @return
     */
    public Map<String, String> definitionMap() {
        Map<String, String> definitionMap = new HashMap<>();
        //游客，开发权限
//        definitionMap.put("/guest/**", "anon");
        // 配置不会被拦截的链接 顺序判断
        definitionMap.put("/login", "anon"); //登录接口排除
        definitionMap.put("/logout", "anon"); //登出接口排除
        definitionMap.put("/getCheckCode", "anon"); //登出接口排除
        definitionMap.put("/", "anon");
        definitionMap.put("/**/*.js", "anon");
        definitionMap.put("/**/*.css", "anon");
        definitionMap.put("/**/*.html", "anon");
        definitionMap.put("/**/*.jpg", "anon");
        definitionMap.put("/**/*.png", "anon");
        definitionMap.put("/**/*.ico", "anon");
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
    public MyRealm getRealm() {
        return new MyRealm();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
