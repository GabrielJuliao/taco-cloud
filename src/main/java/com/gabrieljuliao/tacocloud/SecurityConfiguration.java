package com.gabrieljuliao.tacocloud;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "taco.security")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    //injected by config props
    private String usersQuery;
    //injected by config props
    private String authoritiesQuery;


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("username").password(encoder().encode("password")).authorities("USER");
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(authoritiesQuery).passwordEncoder(new BCryptPasswordEncoder());

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
//        security.authorizeRequests().antMatchers("/design", "/orders")
//                .hasRole("USER").antMatchers("/", "/**")
//                .permitAll().and().formLogin()
//                .loginPage("/SignIn")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/design").and().logout().logoutSuccessUrl("/").and().csrf().disable();

        security.authorizeRequests().antMatchers("/", "/**").permitAll().and().formLogin()
                .loginPage("/SignIn")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/design").and().logout().logoutSuccessUrl("/").and().csrf().disable();

        ///allow h2 console
        security.headers().frameOptions().sameOrigin();
    }
}
