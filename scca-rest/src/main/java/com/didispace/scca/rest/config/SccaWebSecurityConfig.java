package com.didispace.scca.rest.config;

import com.didispace.scca.rest.SccaRestProperties;
import com.didispace.scca.rest.service.LogsService;
import com.didispace.scca.rest.service.SccaUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
public class SccaWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SccaRestProperties sccaRestProperties;

    private final LogsService logsService;

    private final SccaUserDetailsService userDetailsService;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String contextPath = sccaRestProperties.getContextPath();
        http.authorizeRequests()
                .antMatchers(contextPath + "/**").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().loginPage(contextPath + "/login").successHandler(authenticationSuccessHandler()).failureHandler(authenticationFailureHandler()).permitAll()
                .and().rememberMe().alwaysRemember(true)
                .and().logout().logoutUrl(contextPath + "/logout").logoutSuccessHandler(logoutSuccessHandler()).permitAll()
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SccaAuthenticationSuccessHandler(this.logsService);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new SccaAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new SccaLogoutSuccessHandler();
    }

}
