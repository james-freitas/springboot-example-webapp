package com.leanstack.ws.actuator.health;

import com.leanstack.ws.model.Greeting;
import com.leanstack.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GreetingHealthIndicator implements HealthIndicator {

    @Autowired
    private GreetingService greetingService;

    @Override
    public Health health() {

        Collection<Greeting> greetings = greetingService.findAll();

        if(greetings == null || greetings.size() == 0) {
            return Health.down().withDetail("count", greetings.size()).build();
        }
        return Health.up().withDetail("count", greetings.size()).build();
    }
}
