package com.example.demo.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by NULL on 2019/7/25.
 */
@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired(required = false)
    private Registration registration; // 服务注册

    @Autowired
    private DiscoveryClient discoveryClient;// 服务发现客户端


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        ServiceInstance instance = serviceInstance();
        logger.info("/hello ,host:"+instance.getHost()+",service_id:"+instance.getServiceId());
        return "hello word";
    }


    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = discoveryClient.getInstances(registration.getServiceId());
        if(list != null && list.size() > 0){
            for (ServiceInstance itm:list) {
                if (itm.getPort() == 8080){
                    return itm;
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/test")
    public String test() {
        return "test";

    }
}
