package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // default는 ORDINAL 이지만 숫자로 저장하기 때문에 나중에 꼬일 수가 있어서 꼭 STRING으로 해야함
    private DeliveryStatus status; // READY, COMP

}
