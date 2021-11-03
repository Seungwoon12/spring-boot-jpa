package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    /**
     * ItemService는 ItemRepository에 위임만 하는 느낌임. 그래서 간단함
     * 이런 경우 Controller에서 Repository에 바로 접근하는 것을 생각해 볼 필요도 있음. 딱히 문제가 없으니깐
     */

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 엔티티를 변경할때는 항상 변경감지를 사용해라. merge는 사용하지마라
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        /**
         * 아래와 같이만 해줘도 JPA가 알아서 엔티티의 변경을 감지하고 update를 쳐줌. 변경감지 방식임.
         * 내가 update 칠 필드들만 set해줘야함. merge는 set으로 변경 안해준 필드의 값을 null로 바꿔 넣기 때문에 위험함
         * 이렇게 트랜잭션 안에서 엔티티를 조회해야 영속 상태로 조회가 됨. 그리고 그 값을 변경해야 더티체킹(변경감지)이 일어나고 트랜잭션이 커밋될 때 flush가 일어나면서 db에 업데이트 쿼리가 날라감
         */
        Item findItem = itemRepository.findOne(itemId);
//        findItem.change(price, name, stockQuantity); // 실제로는 이런식으로 메서드 만들어서 변경하기. 그래야 추적 및 관리하기 쉬움
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(long itemId) {
        return itemRepository.findOne(itemId);
    }
}
