package app.josue.challengecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChallengeCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChallengeCalculatorApplication::main)
                .with(TestChallengeCalculatorApplication.class)
                .run(args);
    }

}
