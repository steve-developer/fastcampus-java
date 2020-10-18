package com.fastcampus.java.repository;

import com.fastcampus.java.component.LoginUserAuditorAware;
import com.fastcampus.java.config.JpaConfig;
import com.fastcampus.java.model.entity.OrderGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
@DisplayName("ItemRepositoryTest 테스트")
@Import({JpaConfig.class, LoginUserAuditorAware.class})
public class OrderGroupRepositoryTest {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void create(){

        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setStatus("COMPLETE");
        orderGroup.setOrderType("ALL");
        orderGroup.setRevAddress("서울시 강남구");
        orderGroup.setRevName("홍길동");
        orderGroup.setPaymentType("CARD");
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(1);
        orderGroup.setOrderAt(LocalDateTime.now().minusDays(2));
        orderGroup.setArrivalDate(LocalDateTime.now());
        //orderGroup.setCreatedAt(LocalDateTime.now()); // LoginUserAuditorAware 적용으로 자동 createdAt, createdBy 설정
        //orderGroup.setCreatedBy("AdminServer"); // LoginUserAuditorAware 적용으로 자동 createdAt, createdBy 설정
        //orderGroup.setUserId(1L);

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        Assertions.assertNotNull(newOrderGroup);

    }

}
