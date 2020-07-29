package fudan.se.lab2.security;

//import fudan.se.lab2.security.jwt.JwtRequestFilter;
import fudan.se.lab2.security.jwt.JwtRequestFilter;
import fudan.se.lab2.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author LBW
 */
@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtUserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO: Configure your auth here. Remember to read the JavaDoc carefully.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: you need to configure your http security. Remember to read the JavaDoc carefully.


        http.cors().and().csrf().disable();
        http.authorizeRequests()
                //.antMatchers((HttpMethod.POST)).permitAll()
                .antMatchers("/user/**").authenticated()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("Admin");

        // We dont't need CSRF for this project.
        // http.csrf().disable();
        // Make sure we use stateless session; session won't be used to store user's state.
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //.and()
        //     .antMatchers("/welcome").permitAll()
                //.authorizeRequests()
               // .antMatchers("/").permitAll()
               // .antMatchers("/login").permitAll()
               // .antMatchers("/index").permitAll()
               // .antMatchers("/user").permitAll()
               // .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Hint: Now you can view h2-console page at `http://IP-Address:<port>/h2-console` without authentication.
        web.ignoring()
                .antMatchers("/h2-console/**","/#/home/login","/#/home/register","/error","/home");


    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
