package classic;


import java.util.ArrayList;

class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}


public class IntervalsK {


    //https://www.nowcoder.com/practice/02418bfb82d64bf39cd76a2902db2190
    //在合并后的有序区间数组中，插入一个区间，和原有的区间合并
    //例如 1,3]，[6,9]，插入[2,5]，返回[1,5],[6,9]
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> res = new ArrayList<>();
        int i = 0;
        for (; i < intervals.size(); i++) {
            if (newInterval.start > intervals.get(i).end) {
                res.add(intervals.get(i));
            } else if (newInterval.end < intervals.get(i).start) {
                break;
            } else {
                newInterval.start = Math.min(intervals.get(i).start, newInterval.start);
                newInterval.end = Math.max(intervals.get(i).end, newInterval.end);
            }
        }
        res.add(newInterval);
        for (; i < intervals.size(); i++) {
            res.add(intervals.get(i));
        }
        return res;
    }


    //给出一组区间，请合并所有重叠的区间
    //给出[1,3],[2,6],[8,10],[15,18], 返回[1,6],[8,10],[15,18].
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1)
            return intervals;
        intervals.sort((o1, o2) -> {
            if (o1.start < o2.start) {
                return -1;
            } else if (o1.start > o2.start) {
                return 1;
            }
            return 0;
        });
        ArrayList<Interval> res = new ArrayList<>();
        Interval cur = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            if (cur.end < intervals.get(i).start) {
                res.add(cur);
                cur = intervals.get(i);
            } else {
                cur.end = Math.max(intervals.get(i).end, cur.end);
            }
        }
        res.add(cur);

        return res;
    }


}
