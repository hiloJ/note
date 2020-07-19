package simulate.lang;

/**
 * 模仿HashMap
 */
public class MyHashMap<K, V> {
    // 底层基本数据结构 数组
    Node<K, V>[] table;

    // 数组默认大小
    final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    // 数组最大大小
    final int MAX_CAPACITY = 1 << 30;

    // 默认负载因子
    final float DEFAULT_LOAD_FACTOR = .75f;

    // 数组扩容临界值 = 负载因子 * 数组大小
    int threadhold;

    // 记录映射当前容量
    int size;

    // 负载因子
    float loadFactor;

    // 基本节点结构
    static class Node<K, V> {
        int hash;
        K k;
        V v;
        Node<K, V> next;

        Node(int hash, K k, V v, Node next) {
            this.hash = hash;
            this.k = k;
            this.v = v;
            this.next = next;
        }
    }

    /**
     * 无参构造
     */
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public V put(K k, V v) {


        return null;
    }

    /**
     * 哈希值计算
     */
    public int hash(Object object) {
        int hash;
        return object == null ? 0 : (hash = object.hashCode()) ^ (hash >> 16);
    }

    /**
     * 计算大于等于入参的最小2的整数次幂,最大为MAX_CAPACITY
     */
    public int tableSizeOf(int cap) {
        int n = cap - 1;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;

        return n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    /**
     * 数组扩容
     */
    final Node<K, V>[] resize() {
        /**
         *  获取扩容前的数组、数组长度、扩容临界值
         */
        Node<K, V>[] oldTab = table;
        int oldLength = table == null ? 0 : table.length;
        int oldThreadhold = threadhold;

        // 初始化新的数组的长度、临界值
        int newLength, newThreadhold = 0;

        if (oldLength > 0) {
            // 数组已经初始化
            if (oldLength >= MAX_CAPACITY) {
                // 数据长度达到最大值，不再进行扩容
                return oldTab;
            } else if ((newLength = oldLength << 1) <= MAX_CAPACITY && (oldLength >= DEFAULT_INITIAL_CAPACITY)) {
                newThreadhold = oldThreadhold << 1;
            }
        } else if (oldThreadhold > 0) {
            // 数组未初始化，使用扩容临界值作为数组的长度
            newLength = oldThreadhold;
        } else {
            // 数组和扩容临界值都没有初始化，使用默认值
            newLength = DEFAULT_INITIAL_CAPACITY;
            newThreadhold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        }

        threadhold = newThreadhold;

        Node<K, V>[] newTab = new Node[newLength];

        // 旧数组元素转移到新的数组
        if (oldTab != null) {

        }


        return newTab;
    }
}
