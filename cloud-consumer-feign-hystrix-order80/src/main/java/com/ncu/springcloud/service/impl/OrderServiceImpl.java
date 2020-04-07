package com.ncu.springcloud.service.impl;

import com.ncu.springcloud.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "OrderService fallback paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "OrderService fallback paymentInfo_TimeOut";
    }
}
