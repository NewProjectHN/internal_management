package Com.IFI.InternalTool.Config;


import java.util.Collections;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import Com.IFI.InternalTool.Security.CustomUserDetailsService;
import Com.IFI.InternalTool.Security.JwtAuthenticationEntryPoint;
import Com.IFI.InternalTool.Security.JwtAuthenticationFilter;
import Com.IFI.InternalTool.Security.LDAPPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)throws Exception {
    	//TODO: Use for testing only
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        // TODO: Use for real domain connect
//        authenticationManagerBuilder
//                .ldapAuthentication()
//                .userDnPatterns("sAMAccountName={0}")
//                .userSearchFilter("sAMAccountName={0}")
//                .contextSource().url("ldap://10.225.0.51:3268/DC=ifisolution,DC=local").managerDn("IFISOLUTION\\ndlong").managerPassword("Ifi@082018").and()
////                .passwordCompare()
////                .passwordEncoder(new LDAPPasswordEncoder())
////                .passwordAttribute("userPassword")
//        ;
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return  new DefaultSpringSecurityContextSource(
                Collections.singletonList("ldap://10.225.0.51:3268"), "DC=ifisolution,DC=local");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LDAPPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
         .cors()
             .and()
         .csrf()
             .disable()
         .exceptionHandling()
             .authenticationEntryPoint(unauthorizedHandler)
             .and()
         .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
         .authorizeRequests()
             .antMatchers("/",
                 "/favicon.ico",
                 "/**/*.png",
                 "/**/*.gif",
                 "/**/*.svg",
                 "/**/*.jpg",
                 "/**/*.html",
                 "/**/*.css",
                 "/**/*.js")
                 .permitAll()
             .antMatchers("/api/auth/**")
                 .permitAll()
             .anyRequest()
                 .authenticated();
        // Add our custom JWT security filter

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    public static void main (String [] args){

		String username = "ndlong";
		String password = "Ifi@052018";
//		String base = "DC=ifisolution,DC=local";
//		String dn = "uid=" + username + "," + base;
        String ldapURL = "ldap://10.225.0.51:3268";

        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, ldapURL);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        // environment.put(Context.SECURITY_PRINCIPAL, dn);
        environment.put(Context.SECURITY_CREDENTIALS, password);

        environment.put(Context.SECURITY_PRINCIPAL, username + "@IFISOLUTION");
        try {
            InitialDirContext a = new InitialDirContext(environment);

            // check username
            // List<SysUser> listUser = userDAO.getAllUser();
            // for(SysUser user : listUser) {a
            // if(user.getUsername() != username) {
            // userDAO.saveUser(user);
            // }
            // }
            // user is authenticated

            // return userBean;
            System.out.println("SUCCESS");
        } catch (AuthenticationException ex) {
			System.out.println("Fail");

        } catch (NamingException ex) {
            System.out.println("ERROR connect LDAP");
            ex.printStackTrace();

        }
    }

}