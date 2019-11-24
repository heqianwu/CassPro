import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class testK {

    static final List<ListNode> listK= new ArrayList<ListNode>();


    public static void main(String[] args) throws Exception{


        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");
        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);


        String str1="aaa";
        String str2="aaa";
        System.out.println("===========test1============");
        System.out.println(str1==str2);


        String str3=new String("aaa");
        String str4=new String("aaa");
        System.out.println("===========test2============");
        System.out.println(str3==str4);
        char[] valuek = (char[]) valueFieldOfString.get(str4);
        //改变value所引用的数组中的第5个字符
        valuek[1] = '&';
        System.out.println("str3 = " + str3);
        System.out.println("str4 = " + str4);




        String s = "Hello World";
        String sss="Hello World";
        System.out.println("s = " + s); //Hello World
        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);
        //改变value所引用的数组中的第5个字符
        value[5] = '&';
        System.out.println("s = " + s);  //Hello_World
        System.out.println("KKKK");
        System.out.println("Hello World");
        String kkk="Hello World";
        System.out.println("sss： "+sss);
        System.out.println("kkk： "+kkk);

        ListNode kNode =new ListNode(32);
        listK.add(kNode);
        kNode.val=33;
        for(ListNode listNode:listK){
            System.out.println(listNode.val);
        }

    }
}
