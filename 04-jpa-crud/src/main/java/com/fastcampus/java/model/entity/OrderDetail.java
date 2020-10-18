package com.fastcampus.java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // order_detail
@ToString(exclude = {"user","item"})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderAt;

    // N : 1
    @ManyToOne
    private User user; // user_id

    // N : 1
    @ManyToOne
    private Item item;
}
