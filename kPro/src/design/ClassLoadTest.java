package design;

//https://blog.csdn.net/u010425776/article/details/51251430
public class ClassLoadTest {
    public static void main(String[] args) {
        System.out.println(Fu.name);
        System.out.println(Zi.name);
        Zi[] ziList=new Zi[10];
    }
}

class Fu {
    public static final String name = "FU_CLASS";

    static {
        System.out.println("父类Fu被初始化！");
    }
}


class Ku {
    public static String name = "KU_CLASS";

    static {
        System.out.println("父类KU被初始化！");
    }
}

class Zi extends Ku {
    static {
        System.out.println("子类Zi被初始化！");
    }
}