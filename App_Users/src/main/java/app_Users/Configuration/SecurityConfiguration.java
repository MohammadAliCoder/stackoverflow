package app_Users.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"app_Users.*"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//.antMatchers("/Questions/**").hasAnyAuthority("ADMIN","User")
        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/Home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/Profile").permitAll()
                .antMatchers("/Questions").permitAll()
                .antMatchers( "/Solutions/**").hasAnyAuthority("ADMIN","User")
                .antMatchers("/AddQuestions").hasAnyAuthority("ADMIN","User")
				.antMatchers("/PrivateQuestions").hasAnyAuthority("ADMIN","User")
                .and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/Home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().httpBasic();



    }

    @Override
    public void configure(WebSecurity web)  {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/css/**/**"
                      ,"/css/Home/**" ,"/css/Profile/**","/css/Questions/**","/css/AddQuestion/**"
						,"/css/PrivateQuestions/**","/css/Solutions/**");
    }

}