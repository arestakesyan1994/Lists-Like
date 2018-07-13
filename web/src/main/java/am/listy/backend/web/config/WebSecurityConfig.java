package am.listy.backend.web.config;

//import am.listy.backend.api.security.CurrentUserDetailServiceImpl;
import am.listy.backend.web.security.CurrentUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CurrentUserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/profile-admin").hasAuthority("ADMIN")
                .antMatchers("/addCategory").hasAuthority("ADMIN")
                .antMatchers("/addSubject").hasAuthority("ADMIN")
                .antMatchers("/addRegion").hasAuthority("ADMIN")
                .antMatchers("/booking-list").hasAuthority("ADMIN")
                .antMatchers("/dashboard-reviews").hasAuthority("ADMIN")
                .antMatchers("/user-profile").hasAuthority("USER")
                .antMatchers("/contact-us").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/loginSuccess").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/add-listings").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/guest-profile").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/update-user").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/listings").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/edit-listings").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/oders").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/profile").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/loginSuccess")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



