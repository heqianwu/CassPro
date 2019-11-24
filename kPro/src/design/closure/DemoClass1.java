package design.closure;

public class DemoClass1 {
    private int length = 0;

    //private|public
    private class InnerClass implements ILog {
        @Override
        public void Write(String message) {
            //DemoClass1.this.length = message.length();
            length = message.length();
            System.out.println("DemoClass1.InnerClass:" + length);
        }
    }

    public void resetLength(int k) {
        length = k;
    }

    public int getLength() {
        return length;
    }

    public ILog logger() {
        return new InnerClass();
    }

    public static void main(String[] args) {


        String str = "haha";
        new Thread() {
            @Override
            public void run() {
                System.out.println(str);
            }
        }.start();

        DemoClass1 demoClass1 = new DemoClass1();
        demoClass1.logger().Write("abc");

        //.new
        DemoClass1 dc1 = new DemoClass1();
        InnerClass ic = dc1.new InnerClass();
        ic.Write("abcde");

        dc1.resetLength(20);

        System.out.println(dc1.getLength());

    }
}