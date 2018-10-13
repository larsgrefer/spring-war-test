package de.larsgrefer.springtest.parent;

import de.larsgrefer.springtest.child1.ChildContext1;
import de.larsgrefer.springtest.child2.ChildContext2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@SpringBootApplication
@RestController
public class ParentContext extends SpringBootServletInitializer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ParentContext.class)
                .child(ChildContext1.class)
                .sibling(ChildContext2.class);
    }

    @Bean
    public ApplicationRunner parentRunner() {
        return args -> {
            ApplicationContext ctx = applicationContext;

            while (ctx != null) {
                log.info("{} - {} - {}", ctx.getId(), ctx.getApplicationName(), ctx.getDisplayName());
                ctx = ctx.getParent();
            }
        };
    }

    @GetMapping("/")
    public String getRootContext(HttpServletRequest request) {
        return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getId();
    }
}
