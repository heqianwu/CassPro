import java.util.ArrayList;

public class SolutionGrayCode {

    public ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> res=new ArrayList<>();
        res.add(0);
        for(int i=0;i<n;i++){
            int k=res.size();
            for(int j=k-1;j>=0;j--){
                int kkk = (int)Math.pow(2, i);
                res.add(res.get(j)+kkk);
            }
        }
        String kkk="asdf";

        return res;
    }

}
