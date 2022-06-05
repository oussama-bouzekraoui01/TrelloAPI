package com.example.trello.googleCalendar;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class CalendarSecurity extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(HttpMethod.DELETE).permitAll()
                .antMatchers(HttpMethod.PUT).permitAll()
//                    .mvcMatchers("/api/public").permitAll()
//                    .mvcMatchers("/api/private").authenticated()
//                    .mvcMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages")
//                    .and().cors()
//                    .and().oauth2ResourceServer().jwt();
                .anyRequest().authenticated();
//            http.authorizeRequests()
//                    .mvcMatchers("api/v1/calendar").authenticated()
//                    .mvcMatchers("api/v1/gmail").authenticated()
//                    .and().cors()
//                    .and().oauth2ResourceServer().jwt();
    }
}
