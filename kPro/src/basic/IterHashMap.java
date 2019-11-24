package basic;

import java.util.*;

public class IterHashMap {

    public static void main(String[] args) {
        HashMap<String, Integer> hMap = new HashMap<>();
        hMap.put("aaa", 33);
        iter1(hMap);
        iter2(hMap);
        iter3(hMap);
    }

    public static void iter1(HashMap hmap) {
        String key;
        Integer value;
        Iterator<Map.Entry<String, Integer>> iter = hmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            // 获取key
            key = entry.getKey();
            // 获取value
            value = entry.getValue();
            System.out.println("key: " + key + "    value:" + value);
        }
    }

    public static void iter2(HashMap hmap) {
        String key;
        Integer value;
        Iterator iter = hmap.keySet().iterator();
        while (iter.hasNext()) {
            // 获取key
            key = (String) iter.next();
            // 根据key，获取value
            value = (Integer) hmap.get(key);
            System.out.println("key: " + key + "    value:" + value);
        }

    }

    public static void iter3(HashMap hmap) {
        String key;
        Integer value;
        Collection c = hmap.values();
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            value = (Integer) iter.next();
            System.out.println("value:" + value);

        }
    }


    /*
     * 通过Iterator遍历HashSet。推荐方式
     */
    private static void iteratorHashSet(HashSet set) {
        for (Iterator iterator = set.iterator();
             iterator.hasNext(); ) {
            System.out.printf("iterator : %s\n", iterator.next());
        }
    }

    /*
     * 通过for-each遍历HashSet。不推荐！此方法需要先将Set转换为数组
     */
    private static void foreachHashSet(HashSet set) {
        String[] arr = (String[]) set.toArray(new String[0]);
        for (String str : arr)
            System.out.printf("for each : %s\n", str);
    }

    /*
     * 通过for-each遍历HashSet。不推荐！此方法需要先将Set转换为数组
     */
    private static void foreachHSet(HashSet<String> set) {
        for (String str : set)
            System.out.printf("for each : %s\n", str);
    }

    private static void foreachArrayList(ArrayList<String> aList) {
        for (String str : aList) {
            System.out.println(str);
        }
    }

}
