package com.leanstack.ws.service;

import com.leanstack.ws.model.Greeting;

import java.util.concurrent.Future;

public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);

}
