import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class WordLadderQueue {
    public int ladderLength(String start, String end, HashSet<String> dict) {
        LinkedList<String> queue = new LinkedList<>();
        queue.offer(start);
        dict.remove(start);
        int res = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                String str = queue.poll();
                size--;
                if (isMatchByOneChange(str, end)) {
                    return res + 1;
                }
                for (Iterator<String> iter = dict.iterator(); iter.hasNext(); ) {
                    String sktr = iter.next();
                    if (isMatchByOneChange(str, sktr)) {
                        queue.offer(sktr);
                        iter.remove();
                    }
                }
            }
            res++;
        }
        return 0;

    }

    private boolean isMatchByOneChange(String str1, String str2) {
        int count = 0;
        int length = str1.length();
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                count++;
            }
        }
        if (count != 1)
            return false;
        return true;
    }
}
