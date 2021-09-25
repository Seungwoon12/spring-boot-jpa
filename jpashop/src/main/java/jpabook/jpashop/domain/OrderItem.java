package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // @XToOne(@OneToOne, @ManyToOne은 default가 FetchType.EAGER인데 무조건 이걸 FetchType.LAZY로 다 바꿔줘야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // @XToOne(@OneToOne, @ManyToOne은 default가 FetchType.EAGER인데 무조건 이걸 FetchType.LAZY로 다 바꿔줘야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량


}
