package com.test.xyz.daggersample.service.impl;

import com.test.xyz.daggersample.service.api.HelloService;

public class HelloServiceManager implements HelloService {

    @Override
    public String greet(String userName) {
        return "Hello " + userName + "!";
    }
}
