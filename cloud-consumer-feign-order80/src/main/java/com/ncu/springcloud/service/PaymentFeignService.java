package com.ncu.springcloud.service;

import com.ncu.springcloud.common.ResponseData;
import com.ncu.springcloud.entity.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping(value = "/payment/get/{id}")
    ResponseData<Payment> getPaymentById(@PathVariable("id") Long id);
}
