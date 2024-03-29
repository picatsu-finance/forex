package com.picatsu.financeforex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.patriques.AlphaVantageConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;





@Configuration
public class Config {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.timeout}")
    private int timeout;


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger doc for the Finance-ash project")
                        .version("0.0.1")
                        .description("Finance-Forex API ")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public AlphaVantageConnector beanAlphaApiConnector() {


        return new AlphaVantageConnector(apiKey, timeout);
    }


}
