package design.closure;


//闭包（Closure）是一种能被调用的对象，它保存了创建它的作用域的信息
//非静态内部类、非静态匿名内部类会持有外部对象的引用。  匿名内部类访问的"局部变量"要用final修饰(或者由编译器添加)
//在JAVA中，闭包是通过“接口+内部类”实现
public class TestClosure {

    public static void main(String[] args) {
        TeachableProgrammer tp = new TeachableProgrammer("李刚");// 该示例来源于李刚老师的疯狂讲义
        // 直接调用TeachableProgrammer从Programmer类继承下来的work方法
        tp.work();
        // 表明上看是调用的Closure的work方法，实际上是通过通过work方法回调TeachableProgrammer的teach方法
        tp.getCallbackReference("数学").work();

    }

}
