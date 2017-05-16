package example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    @Value("${password}")
    String password;

    @Value("${spring.datasource.password}")
    String springDatasourcePassword;
    @Value("${spring.datasource.url}")
    String springDatasourceUrl;

    @PostConstruct
    private void postConstruct() {
        System.out.println("##########################");
        System.out.println(password);
        System.out.println("##########################");
        System.out.println("##########################");
        System.out.println(springDatasourcePassword);
        System.out.println("##########################");
        System.out.println(springDatasourceUrl);
        System.out.println("##########################");
    }

}
