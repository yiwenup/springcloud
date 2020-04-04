package com.ncu.springcloud.controller;

import com.ncu.springcloud.common.ResponseData;
import com.ncu.springcloud.entity.Payment;
import com.ncu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/postForObject/create")
    public ResponseData<Payment> create1(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, ResponseData.class);
    }

    @GetMapping("/consumer/payment/postForEntity/create")
    public ResponseData<Payment> create2(Payment payment) {
        ResponseEntity<ResponseData> res = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, ResponseData.class);
        log.info(res.toString());
        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return new ResponseData<Payment>(777, "err");
        }
    }

    @GetMapping("consumer/payment/getForObject/{id}")
    public ResponseData<Payment> getPayment1(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, ResponseData.class);
    }

    @GetMapping("consumer/payment/getForEntity/{id}")
    public ResponseData<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<ResponseData> res = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, ResponseData.class);
        log.info(res.toString());
        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return new ResponseData<Payment>(777, "error");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances==null || instances.size()<=0) {
            return null;
        }
        ServiceInstance instance = loadBalancer.instances(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb", String.class);
    }
}
