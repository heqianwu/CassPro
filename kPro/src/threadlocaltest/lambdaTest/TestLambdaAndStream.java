package threadlocaltest.lambdaTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLambdaAndStream {

    public static void main(String[] args) {
        List<String> strList = Arrays.asList(new String[]{"Ni", null, "Hao", null, "Lambda"});
        long numSize = strList.stream().filter(str -> str != null).count();
        System.out.println(strList.size() + ":" + numSize);
        Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::print);
        System.out.println();

        List<Integer> nums = Arrays.asList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        List<Integer> numsWithoutNull1 = nums.stream().filter(num -> num != null).
                collect(Collectors.toList());

        List<Integer> numsWithoutNull2 = nums.stream().filter(num -> num != null).
                collect(() -> new ArrayList<Integer>(),
                        (list, item) -> list.add(item),
                        (list1, list2) -> list1.addAll(list2));

        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("ints sum is:" + ints.stream().reduce(0, (sum, item) -> sum + item));
        System.out.println("ints sum is:" + ints.stream().count());


        System.out.println(ints.stream().allMatch(item -> item < 100));


        testLambda();


    }

    private static void testLambda() {

        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();

    }


}






