package com.fastcampus.java.service;

import com.fastcampus.java.model.entity.OrderGroup;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.request.OrderGroupApiRequest;
import com.fastcampus.java.model.network.response.OrderGroupApiResponse;
import com.fastcampus.java.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    private final UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        return Optional.ofNullable(request.getData())
                .map(body ->{
                    OrderGroup orderGroup = OrderGroup.builder()
                            .status(body.getStatus())
                            .orderType(body.getOrderType())
                            .revAddress(body.getRevAddress())
                            .revName(body.getRevName())
                            .paymentType(body.getPaymentType())
                            .totalPrice(body.getTotalPrice())
                            .totalQuantity(body.getTotalQuantity())
                            .orderAt(LocalDateTime.now())
                            .user(userRepository.getOne(body.getUserId()))
                            .build();

                    return orderGroup;
                })
                .map(newOrderGroup -> baseRepository.save(newOrderGroup))
                .map(newOrderGroup -> response(newOrderGroup))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {

        return Optional.ofNullable(request.getData())
                .map(body ->{
                    return baseRepository.findById(body.getId())
                        .map(orderGroup -> {
                            orderGroup
                                    .setStatus(body.getStatus())
                                    .setOrderType(body.getOrderType())
                                    .setRevAddress(body.getRevAddress())
                                    .setRevName(body.getRevName())
                                    .setPaymentType(body.getPaymentType())
                                    .setTotalPrice(body.getTotalPrice())
                                    .setTotalQuantity(body.getTotalQuantity())
                                    .setOrderAt(body.getOrderAt())
                                    .setArrivalDate(body.getArrivalDate())
                                    .setUser(userRepository.getOne(body.getUserId()))
                            ;
                            return orderGroup;
                        })
                        .map(changeOrderGroup -> baseRepository.save(changeOrderGroup))
                        .map(this::response)
                        .orElseGet(()->Header.ERROR("데이터 없음"));
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(orderGroup -> {
                    baseRepository.delete(orderGroup);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<OrderGroupApiResponse> response(OrderGroup orderGroup){

        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();

        return Header.OK(body);
    }
}
