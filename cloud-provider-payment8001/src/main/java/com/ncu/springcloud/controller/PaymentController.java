package com.ncu.springcloud.controller;

import com.ncu.springcloud.common.ResponseData;
import com.ncu.springcloud.entity.Payment;
import com.ncu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public ResponseData create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("result => " + result);
        if (result > 0) {
            return new ResponseData(200, "插入成功", result);
        } else {
            return new ResponseData(444, "插入失败");
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public ResponseData getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        log.info("result => " + result);
        if (result != null) {
            return new ResponseData(200, "查询成功", result);
        } else {
            return new ResponseData(444, "查询"+id+"失败");
        }
    }
}
