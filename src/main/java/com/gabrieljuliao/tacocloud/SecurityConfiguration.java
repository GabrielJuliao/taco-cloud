package com.gabrieljuliao.tacocloud;

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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(DataSource dataSource, UserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("username").password(encoder().encode("password")).authorities("USER");
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username=?"
                )
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username=?"
                ).passwordEncoder(new BCryptPasswordEncoder());

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests().antMatchers("/design", "/orders")
                .hasRole("USER").antMatchers("/", "/**")
                .permitAll().and().formLogin()
                .loginPage("/SignIn")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/design").and().logout().logoutSuccessUrl("/").and().csrf().disable();

//        security.authorizeRequests().antMatchers("/", "/**").permitAll().and().formLogin()
//                .loginPage("/SignIn")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/design").and().logout().logoutSuccessUrl("/").and().csrf().disable();

        ///allow h2 console
        security.headers().frameOptions().sameOrigin();
    }
}
