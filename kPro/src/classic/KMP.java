package classic;

public class KMP {

    public static void main(String[] args){
        KMP kmp=new KMP();
        System.out.println(kmp.kmp("BBCWABCDABWABCDABCDABDE","ABCDABD"));
        System.out.println(kmp.kmp("sdfefereaaaaaweegg","aaaaaa"));
    }

    private int kmp(String originStr, String subString) {
        if (subString == null || subString.length() == 0) {
            return 0;
        }
        if(originStr == null || originStr.length() < subString.length()){
            return -1;
        }
        int[] next = getNext(subString);
        for(int i = 0,j=0; i < originStr.length(); i++){
            while(j > 0 && originStr.charAt(i) != subString.charAt(j)){
                j = next[j - 1];
            }
            if(originStr.charAt(i) == subString.charAt(j)){
                j++;
            }
            if(j == subString.length()){
                return i-j+1;
            }
        }
        return -1;
    }

    private int[] getNext(String str) {
        int[] next = new int[str.length()];
        next[0] = 0;
        int j;
        for(int i = 1; i < str.length(); i++){
            j=next[i-1];
            while(j > 0 && str.charAt(j) != str.charAt(i)){
                j = next[j - 1];
            }
            if(str.charAt(i) == str.charAt(j)){
                next[i]=j+1;
            }
            else
                next[i]=0;
        }
        return next;
    }
}