package com.fastcampus.java.controller.api;

import com.fastcampus.java.controller.CrudController;
import com.fastcampus.java.model.entity.OrderGroup;
import com.fastcampus.java.model.network.request.OrderGroupApiRequest;
import com.fastcampus.java.model.network.response.OrderGroupApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController extends CrudController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {


}
