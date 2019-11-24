package basic;

class Testkkk{
    public Testkkk(String str){
        System.out.println("testkkk:"+str);
    }
}

//先类加载：(子静态变量，子静态代码块)，(父静态变量，父静态代码块)类的初始化
//对象创建：(父成员属性，父非静态代码块)，父构造方法，(子成员属性，子非静态代码块)，子构造方法
//关键点：1、构造方法最后调用。2、先父后子。 3、属性和代码块的先后由本身位置的先后确定。
class HelloA {

    //静态代码块
    static {
        System.out.println("static A 父类静态代码块");
    }
    public static Testkkk testkkk=new Testkkk("static:aaa");

    //构造方法
    public HelloA() {
        System.out.println("Hello A!父类构造方法");
    }

    //非静态代码块
    {
        System.out.println("i am A class.父类非静态代码块");
    }

    public Testkkk kkk=new Testkkk("aaa");
}

public class HelloB extends HelloA {
    public static Testkkk testkkk=new Testkkk("static:bbb");
    public Testkkk kkk=new Testkkk("bbb");
    //构造方法
    public HelloB() {
        System.out.println("Hello B!子类构造方法");
    }

    //非静态代码块
    {
        System.out.println("i am B class.子类非静态代码块");
    }

    //静态代码块
    static {
        System.out.println("static B 子类静态代码块");
    }

    //run时会先加载HelloB类
    public static void main(String[] args) {
        System.out.println("======start=========");
//        new HelloA();
        new HelloB();
        System.out.println("========end===========");
    }
}
