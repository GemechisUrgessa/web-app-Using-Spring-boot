package com.software.project.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      .antMatchers("/adminPage","/content/edit/**")
      .hasRole("ADMIN")
      .antMatchers("/profile","/content/comment/**","/contact/save")
      .authenticated()
      .anyRequest()
      .permitAll()
      .and()
      .formLogin()
      .loginPage("/login")
      .failureForwardUrl("/loginFailed")
      .permitAll()
      .and()
      .logout()
      .permitAll()
      .logoutSuccessUrl("/");















      
      // .authorizeRequests()
      // .antMatchers("/admin","/orderDashBoard")
      // .hasRole("ADMIN")
      // .antMatchers("/order","/profile")
      // .authenticated()
      // .anyRequest()
      // .permitAll()
      // .and()
      // .formLogin()
      // .loginPage("/login")
      // .failureForwardUrl("/loginFailled")
      // .permitAll()
      // .and()
      // .logout().permitAll()
      // .logoutSuccessUrl("/");
   
    }


    


}
