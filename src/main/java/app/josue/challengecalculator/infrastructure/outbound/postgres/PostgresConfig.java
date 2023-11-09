package app.josue.challengecalculator.infrastructure.outbound.postgres;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class PostgresConfig {

    @Bean
    public Scheduler jdbcScheduler(Environment env) {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(
                env.getRequiredProperty("jdbc.connection.pool.size", Integer.class)
        ));
    }

}
