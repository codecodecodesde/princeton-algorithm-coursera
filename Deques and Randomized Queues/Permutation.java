import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wangxuan on 17/2/12.
 */
public class Permutation {
    public static void main(String[] arg){
        RandomizedQueue<String> l = new RandomizedQueue<String>();
        int n=Integer.parseInt(arg[0]);
        int i=0;
        while(!StdIn.isEmpty()){
            l.enqueue(StdIn.readString());
        }

        for(String s:l){
            if(i<n)
                StdOut.println(s);
            else
                break;
            i++;
        }
    }
}
