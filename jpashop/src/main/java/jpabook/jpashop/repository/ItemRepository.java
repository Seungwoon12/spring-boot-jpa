package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // 업데이트랑 완전히 동일한 개념은 아니지만, 일단은 업데이트랑 비슷하다고 이해하면 됨
            // 아이템 id가 있으면 강제 업데이트
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        // 여러개 찾을 때는 jpql을 작성해야함
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
