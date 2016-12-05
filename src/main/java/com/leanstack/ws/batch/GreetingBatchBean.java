package com.leanstack.ws.batch;

import com.leanstack.ws.model.Greeting;
import com.leanstack.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Profile("batch")
@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(
            cron = "${batch.greeting.cron}" )
    public void cronJob(){

        logger.info("> cronjob");

        // Add scheduled logic here
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in the data store.", greetings.size());

        logger.info("< cronjob");
    }

    @Scheduled(
            initialDelayString = "${batch.greeting.initialdelay}",
            fixedRateString = "${batch.greeting.fixedrate}"
    )

    public void fixedRateJobWithInitialDelay(){
        logger.info("> fixedRateJobWithInitialDelay");

        // Add scheduled logic here
        // Simulate job processing time
        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()){
                break;
            }
        } while (true);

        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedRateJobWithInitialDelay");
    }


    @Scheduled(
            initialDelayString = "${batch.greeting.initialdelay}",
            fixedDelayString = "${batch.greeting.fixeddelay}"
    )
    public void fixedDelayJobWithInitialDelay(){
        logger.info("> fixedDelayJobWithInitialDelay");

        // Add scheduled logic here
        // Simulate job processing time
        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()){
                break;
            }
        } while (true);

        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedRateJobWithInitialDelay");
    }


}
