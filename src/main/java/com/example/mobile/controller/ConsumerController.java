package com.example.mobile.controller;

import com.example.mobile.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者
 */
@RestController
@RequestMapping(value = "consumer")
public class ConsumerController {

    @GetMapping(value = "consumerList")
    public Result getConsumerList()
    {

        return null;
    }
}
