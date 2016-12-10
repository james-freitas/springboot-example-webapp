package com.leanstack.ws.web.api;

import com.leanstack.ws.model.Greeting;
import com.leanstack.ws.service.EmailService;
import com.leanstack.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.Future;

@RestController
public class GreetingController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {
        Collection<Greeting> greetings = greetingService.findAll();

        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {
        Greeting greeting = greetingService.findOne(id);
        if (greeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        Greeting savedGreeting = greetingService.create(greeting);
        return new ResponseEntity<>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
        Greeting updatedGreeting = greetingService.update(greeting);
        if (updatedGreeting == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(
            @PathVariable("id") Long id) {

        greetingService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/greetings/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") Long id,
            @RequestParam(
                    value = "wait",
                    defaultValue = "false") boolean waitForAsyncResult) {

        logger.info("> sendGreeting");
        Greeting greeting = null;

        try {
            greeting = greetingService.findOne(id);
            if (greeting == null){
                logger.info("< sendGreeting");
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if(waitForAsyncResult) {
                Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info("- greeting mail sent? {}", emailSent);
            } else {
                emailService.sendAsync(greeting);
            }
        } catch (Exception e) {
            logger.error("A problem ocurred sending the Greeting.", e);
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< sendGreeting");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
