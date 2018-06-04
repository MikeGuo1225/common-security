package com.chentongwe;

//import com.alibaba.fastjson.JSON;
//import com.chentongwe.security.MyUserDetailsService;
//import com.chentongwei.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author chentongwei@bshf360.com 2018-03-26 10:26
 */
@ServletComponentScan
@ComponentScan(basePackages = {"com.chentongwe", "com.chentongwei"})
@SpringBootApplication
@RestController
public class Application extends WebMvcConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello spring security2";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return "hello spring security3";
    }

    @GetMapping("/hello4")
    public String hello4() {
        return "hello spring security4";
    }

    /*@Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        System.out.println(JSON.toJSONString(claims));

        return user;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello spring security";
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/login123")
    public UserDetails login(String username) {
        System.out.println("login进来了");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails;
    }*/


}
