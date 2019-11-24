package iodesign;

import java.io.*;

public class IOTestCancle {

    public static void main(String[] args) throws IOException {
        IOTestCancle ioTest = new IOTestCancle();
        ioTest.testPrintWriter();
    }

    public void testBufferedReader() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {
            while ((s = in.readLine()).length() != 0) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void testPrintWriter() {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter("/Users/hqwk/cancle/transferToTest1.txt", true)));
            pw.println("KKKKKK");
            pw.println("HHHHHH");
            pw.append("WWWWWWW\n");
            pw.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void writeByteToFile() throws IOException {
        String hello = new String("hello word!");
        byte[] byteArray = hello.getBytes();
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        OutputStream os = new FileOutputStream(file);
        os.write(byteArray);
        os.close();
    }

    public void readByteFromFile() throws IOException {
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        byte[] byteArray = new byte[(int) file.length()];
        InputStream is = new FileInputStream(file);
        int size = is.read(byteArray);
        System.out.println("大小:" + size + ";内容:" + new String(byteArray));
        is.close();
    }

    public void writeCharToFile() throws IOException {
        String hello = new String("hello word!");
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        //因为是用字符流来读媒介，所以对应的是Writer，又因为媒介对象是文件，所以用到子类是FileWriter
        Writer os = new FileWriter(file);
        os.write(hello);
        os.close();
    }

    public void readCharFromFile() throws IOException {
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        Reader reader = new FileReader(file);
        char[] byteArray = new char[(int) file.length()];
        int size = reader.read(byteArray);
        System.out.println("大小:" + size + ";内容:" + new String(byteArray));
        reader.close();
    }

    public void readCharFromFilek() {
        FileReader fr = null;
        try {
            fr = new FileReader("Demo.txt");
            //第一种读取方式：read方法，一次读单个字符
            int ch = 0;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("读取流关闭失败");
                }
        }
    }

    public void readCharFromFileh() {
        FileReader fr = null;
        try {
            fr = new FileReader("Demo.txt");
            int len = 0;
            //定义一个字符数组,用于存储读到字符。一般数组的长度都是1024的整数倍。
            char[] buf = new char[1024];
            while ((len = fr.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("读取流关闭失败");
                }
        }
    }

    public void copyFileWithFileRW() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader("D:\\Demo.java");
            fw = new FileWriter("E:\\Demo.java");
            char[] buf = new char[1024];
            int len = 0;
            while ((len = fr.read(buf)) != -1) {
                fw.write(buf, 0, len);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (fr != null)
                try {
                    //关闭流资源
                    fr.close();
                } catch (IOException e) {
                    System.out.println("读取流关闭失败");
                }
            if (fw != null)
                try {
                    //关闭流资源
                    fw.close();
                } catch (IOException e) {
                    System.out.println("输出流关闭失败");
                }
        }
    }

    public void copyFileWithBuffer() {
        BufferedReader bfr = null;
        BufferedWriter bfw = null;
        try {
            //为了提高写入的效率，使用了字符流的缓冲区
            //只要将需要被提高效率的流对象作为参数传递给缓冲区的构造函数即可。
            bfr = new BufferedReader(new FileReader("D:\\Demo.java"));
            bfw = new BufferedWriter(new FileWriter("E:\\Demo.java"));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                bfw.write(line);
                //写入内容换行方法：newLine();
                bfw.newLine();
                bfw.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (bfr != null)
                try {
                    bfr.close();
                } catch (IOException e) {
                    System.out.println("读取流关闭失败");
                }
            if (bfw != null)
                try {
                    bfw.close();
                } catch (IOException e) {
                    System.out.println("输出流关闭失败");
                }
        }
    }

    public void convertByteToChar() throws IOException {
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        InputStream is = new FileInputStream(file);
        Reader reader = new InputStreamReader(is);
        char[] byteArray = new char[(int) file.length()];
        int size = reader.read(byteArray);
        System.out.println("大小:" + size + ";内容:" + new String(byteArray));
        is.close();
        reader.close();
    }

    public void randomAccessFileRead() throws IOException {
        RandomAccessFile file = new RandomAccessFile("/Users/hqwk/cancle/transferToTest1.txt", "rw");
        file.seek(10);
        long pointerBegin = file.getFilePointer();
        byte[] contents = new byte[1024];
        file.read(contents);
        long pointerEnd = file.getFilePointer();
        System.out.println("pointerBegin:" + pointerBegin + "\n" + "pointerEnd:" + pointerEnd + "\n" + new String(contents));
        file.close();
    }

    public void randomAccessFileWrite() throws IOException {
        RandomAccessFile file = new RandomAccessFile("/Users/hqwk/cancle/transferToTest1.txt", "rw");
        file.seek(10);
        long pointerBegin = file.getFilePointer();
        file.write("HELLO WORD".getBytes());
        long pointerEnd = file.getFilePointer();
        System.out.println("pointerBegin:" + pointerBegin + "\n" + "pointerEnd:" + pointerEnd + "\n");
        file.close();
    }

    public void readByBufferedInputStream() throws IOException {
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        byte[] byteArray = new byte[(int) file.length()];
        InputStream is = new BufferedInputStream(new FileInputStream(file), 2 * 1024);
        int size = is.read(byteArray);
        System.out.println("大小:" + size + ";内容:" + new String(byteArray));
        is.close();
    }

    public void readByBufferedReader() throws IOException {
        File file = new File("/Users/hqwk/cancle/transferToTest1.txt");
        Reader reader = new BufferedReader(new FileReader(file), 2 * 1024);
        char[] byteArray = new char[(int) file.length()];
        int size = reader.read(byteArray);
        System.out.println("大小:" + size + ";内容:" + new String(byteArray));
        reader.close();
    }


    public void testInputStreamReader() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String line = null;
        while ((line = in.readLine()) != null) {
            if ("over".equals(line))
                break;
            out.write(line.toUpperCase());
            out.newLine();
            out.flush();
        }
        in.close();
        out.close();
    }


    public void testObjectStream() {
        try {
            File file = new File("/Users/hqwk/cancle/transferToTest1.txt");

            IOClient ms = new IOClient();
            try {
                OutputStream os = new FileOutputStream(file);
                //创建时，需要给予一个outputStream，这个很好理解，
                //因为对象操作肯定是字节操作，不能使用字符操作
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(ms);
                oos.close();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            try {
                InputStream is = new FileInputStream(file);
                ObjectInputStream ios = new ObjectInputStream(is);
                IOClient object = (IOClient) ios.readObject();
                System.out.println(object.toString());
                is.close();
                ios.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
