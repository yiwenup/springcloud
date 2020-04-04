package com.ncu.springcloud.controller;

import com.ncu.springcloud.common.ResponseData;
import com.ncu.springcloud.entity.Payment;
import com.ncu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public ResponseData create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("result => " + result);
        if (result > 0) {
            return new ResponseData(200, "插入成功, serverPort: " + serverPort, result);
        } else {
            return new ResponseData(444, "插入失败");
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public ResponseData getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        log.info("result => " + result);
        if (result != null) {
            return new ResponseData(200, "查询成功, serverPort: " + serverPort, result);
        } else {
            return new ResponseData(444, "查询" + id + "失败");
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri()+"\t"+instance.getInstanceId());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
