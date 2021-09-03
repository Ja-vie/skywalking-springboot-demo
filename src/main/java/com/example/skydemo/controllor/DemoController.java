package com.example.skydemo.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    @Value("${service.remoteAddress}")
    String remoteAddress;
    @Value("${spring.application.name}")
    String applicationName;

    @Autowired
    RestTemplate restTemplate;

    int count;

    @GetMapping("/myerror")
    public int error() {
        return 1/0;
    }

    @GetMapping("/sleep/{duration}")
    public String sleep(@PathVariable Long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sleep "+ duration + " ms";
    }

    @GetMapping("/hello/{param}")
    public String hello(@PathVariable String param) {
        return "hello "+ applicationName + " " + param;
    }

    // a1 -> b -> c -> d
    @GetMapping("/remoteCall")
    public String remoteCall() {
        return restTemplate.getForObject("http://"+remoteAddress+"/remoteCall/"+remoteAddress, String.class);
    }

    @GetMapping("/remoteCall/{ip}")
    public String remoteCallWithIp(@PathVariable String ip) {
        if (!StringUtils.hasText(remoteAddress)) {
            return ip + "  " + applicationName;
        }
        return restTemplate.getForObject("http://"+remoteAddress+"/remoteCall/"+remoteAddress, String.class);
    }
}
