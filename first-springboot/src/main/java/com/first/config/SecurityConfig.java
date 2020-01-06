package com.first.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*Add basic security to the App
 * */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // adicional para segurança em delete ctrl
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf().disable(); // habilitado protege contra links maliciosos que 
		//utilizam sessões não deslogadas

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//"{noop}123" it made the authentication work
		auth.inMemoryAuthentication()
		.withUser("user").password("{noop}123").roles("USER")
		.and().withUser("admin")
				.password("{noop}admin").roles("USER", "ADMIN");

	}

}
