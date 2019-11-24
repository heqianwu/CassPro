package design.closure;

public class DemoClass3 {
    private int length = 0;

    public ILog logger() {
        return new ILog() {
            @Override
            public void Write(String message) {
                length = message.length();
                System.out.println("DemoClass3.AnonymousClass:" + length);
            }
        };
    }
}