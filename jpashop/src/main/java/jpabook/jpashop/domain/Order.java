package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블명을 orders로 하겠다.
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // @XToOne(@OneToOne, @ManyToOne은 default가 FetchType.EAGER인데 무조건 이걸 FetchType.LAZY로 다 바꿔줘야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // fk 이름이 member_id가 된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // @XToOne(@OneToOne, @ManyToOne은 default가 FetchType.EAGER인데 무조건 이걸 FetchType.LAZY로 다 바꿔줘야함
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // 연관 관계의 주인이 되는 것. Order 테이블에서 fk로 설정하는 것
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING) // enum 쓸 때 꼭 이 어노테이션 붙여줘야함
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    // ==연관관계 메서드== //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


}
