package com.hqw.pro.hspboot.util;

public class LogUtil {

    public void info(String... str){
        System.out.print(str[0]);
        for(int i=1;i<str.length;i++){
            System.out.print("  "+str[i]);
        }
        System.out.println();
    }

    public void error(String... str){
        System.out.print(str[0]);
        for(int i=1;i<str.length;i++){
            System.out.print("  "+str[i]);
        }
        System.out.println();
    }

}
