package de.larsgrefer.springtest.child2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ChildContext2 {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ApplicationRunner child2Runner() {
        return args -> {
            ApplicationContext ctx = applicationContext;

            while (ctx != null) {
                log.info("{} - {} - {}", ctx.getId(), ctx.getApplicationName(), ctx.getDisplayName());
                ctx = ctx.getParent();
            }
        };
    }
}
