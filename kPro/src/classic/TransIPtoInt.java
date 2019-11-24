package classic;


/**
 * 0 0b 0e 0x 0B 0E 0X A~F f F d D 大小写是不分的
 *
 * System.out.println(0b101);//二进制:5  （0b开头的）
 * System.out.println(0e1011);//0.0
 * System.out.println(1e3);  //1000.0   1E-3 0.001
 * System.out.println(011);//八进制:9   (0开头的)
 * System.out.println(11);//十进制:11
 * System.out.println(0x11C);//十六进制:284   （0x开头的）
 *
 * System.out.printf("%010x\n",7);//0000000007   按10位十六进制输出，向右靠齐，左边用0补齐
 * System.out.printf("%010o\n",13);//0000000015    按10位八进制输出，向右靠齐，左边用0补齐
 *
 * System.out.printf("%x\n",7);//7   按16进制输出
 * System.out.printf("%o\n",13);//15   按8进制输出
 *
 * System.out.println(Integer.toBinaryString(11));//1011 二进制
 *
 */

public class TransIPtoInt
{
    private int iPToInt(String ip) throws Exception
    {
        ip = ip.trim();
        String regular = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        String[] iparray = ip.split("\\.");
        if (!ip.matches(regular) || iparray.length != 4)
        {
            throw new Exception("Wrong IP.");
        }
        return Integer.parseInt(iparray[0]) << 24 | Integer.parseInt(iparray[1]) << 16
                | Integer.parseInt(iparray[2]) << 8 | Integer.parseInt(iparray[3]);
    }

    private String intToIP(int ipnum)
    {
        return (int) ((ipnum & 0xff000000L) >> 24) + "." + (int) ((ipnum & 0xff0000L) >> 16) + "."
                + (int) ((ipnum & 0xff00L) >> 8) + "." + (int) (ipnum & 0xffL);
    }

    public static void main(String[] args) throws Exception
    {
        TransIPtoInt tp = new TransIPtoInt();
        String ip = "232.132.72.255";
        int ipnum = tp.iPToInt(ip);
        System.out.println(ipnum);
        System.out.println(tp.intToIP(ipnum));
    }
}