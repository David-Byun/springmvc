package hello.springmvc.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>(); //static
    private static Long sequence = 0L; //static 확인 및 multithread 문제로 AtomicLong 사용
    // 다른데서 new로 쓸때 별도로 생성되므로 static을 기재하였음

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        //ArrayList 감싼 이유는 감싸야 store에 대해서 값 변화가 없기 때문임
    }

    //updateParam 별도 class를 만드는게 더나음
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
