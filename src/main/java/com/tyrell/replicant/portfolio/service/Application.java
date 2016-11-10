package com.tyrell.replicant.portfolio.service;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.jms.ConnectionFactory;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@SpringBootApplication
@EnableJms
@EnableSwagger2
public class Application {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        String urlForRemoteClientsToConnectTo = "tcp://localhost:61616";
        broker.addConnector(urlForRemoteClientsToConnectTo);
        broker.setPersistent(false);
        return broker;
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Docket api() {
        String requestPath = "/portfolio.*";
        String restAPITitle = "Portfolio Service REST API";
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(requestPath))
                .build()
                .apiInfo(new ApiInfo(restAPITitle, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}