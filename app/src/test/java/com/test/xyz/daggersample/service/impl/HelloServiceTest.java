package com.test.xyz.daggersample.service.impl;

import com.test.xyz.daggersample.service.api.HelloService;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class HelloServiceTest {
    private static String USER_NAME = "hazems";
    private static String GREET_PREFIX = "Hello ";

    @Inject
    HelloService helloService;

    @Before
    public void setup() {
    }

    @Test
    public void greet_whenUserNameIsValid_shouldGreetUser() {
        //GIVEN
        helloService = new HelloServiceManager();

        //WHEN
        String result = helloService.greet(USER_NAME);

        //THEN
        Assert.assertThat(result, CoreMatchers.containsString(GREET_PREFIX + USER_NAME));
    }
}
