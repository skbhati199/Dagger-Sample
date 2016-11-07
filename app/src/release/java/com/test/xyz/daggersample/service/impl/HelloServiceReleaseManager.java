package com.test.xyz.daggersample.service.impl;

import com.test.xyz.daggersample.service.api.HelloService;

public class HelloServiceReleaseManager implements HelloService {

    @Override
    public String greet(String userName) {
        return "[Release] Hello " + userName + "!";
    }
}
