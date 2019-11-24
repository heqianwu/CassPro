package design.closure;

public class TeachableProgrammer extends Programmer {

    public TeachableProgrammer() {
        super();
    }

    public TeachableProgrammer(String name) {
        super(name);
    }

    // 教学工作任然由TeachableProgrammer定义
    private void teach(String content) {
        System.out.println(getName() + "正在教授" + content);
    }

    private class Closure implements Teachable {
        String content = "";

        Closure(String content) {
            this.content = content;
        }

        @Override
        public void work() {
            System.out.println("当前this： "+this);
            System.out.println("当前外部this： "+TeachableProgrammer.this);
            // 非静态内部类实现Teachable的work方法，作用仅仅是向客户类提供一个回调外部类的途径
            teach(content);
        }
    }

    // 返回一个非静态内部类的引用,允许外部类通过该引用来回调外部类的方法
    public Teachable getCallbackReference(String content) {
        return new Closure(content);
    }
}