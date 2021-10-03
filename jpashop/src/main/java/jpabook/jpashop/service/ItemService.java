package jpabook.jpashop.service;

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

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(long itemId) {
        return itemRepository.findOne(itemId);
    }
}
