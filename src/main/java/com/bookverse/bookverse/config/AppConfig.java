package com.bookverse.bookverse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
//When to Use a Configuration Class with Beans?
//You use it when:
//
//You manually create objects instead of relying on annotations like @Service, @Repository, etc (does the same thing)

//
//You want fine control over object creation (e.g., setting up a 3rd party service or complex configuration)
//
//You need to register external libraries or custom clients as beans
public class AppConfig {

    //Now Spring knows how to provide a RestTemplate bean anywhere:
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //instead of @Service - you could define this bean like that
    //@Bean
    //    public AuthorService authorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    //        return new AuthorServiceImpl(authorRepository, authorMapper);
    //    }
}
