package Gateway;

import java.util.HashMap;

/**
 * Created by simon_000 on 05/05/2016.
 */
public class BiMap<K,V> {

    HashMap<K,V> map = new HashMap<K, V>();
    HashMap<V,K> inversedMap = new HashMap<V, K>();

    void put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
    }

    V get(K k) {
        return map.get(k);
    }

    K getKey(V v) {
        return inversedMap.get(v);
    }

}
