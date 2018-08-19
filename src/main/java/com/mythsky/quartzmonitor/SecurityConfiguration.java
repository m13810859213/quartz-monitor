package com.mythsky.quartzmonitor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);  //取消默认设置
        http.authorizeRequests()
//                .anyRequest().permitAll() //允许任何请求
                .antMatchers("/monitor").authenticated()    // monitor需要安全认证
        .and().formLogin().permitAll()  //允许login页面
        ;
    }
}
