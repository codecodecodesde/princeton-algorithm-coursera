import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.*;
/**
 * Created by wangxuan on 17/2/11.
 */
public class PercolationStats {
    private int gridsize;
    private int trialstime;
    private double[] threshold;
    //private Percolation simulator;
    //TODO:perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0||trials<=0)
            throw new IllegalArgumentException("n or trials out of bounds");
        gridsize=n;
        trialstime=trials;
        threshold=new double[trialstime];
        for(int i=0;i<trials;i++){
            threshold[i]=experiment();
        }
    }

    private double experiment(){
        int row;
        int col;
        double t;
        Percolation simulator = new Percolation(gridsize);//pay attention
        while(!simulator.percolates()){
            row= StdRandom.uniform(1,gridsize+1);
            col=StdRandom.uniform(1,gridsize+1);
            simulator.open(row,col);
        }
        t=(double)simulator.numberOfOpenSites()/(gridsize*gridsize);//?int/int=double
        return t;
    }

    //TODO: sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(threshold);
    }
    //TODO: sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(threshold);
    }
    //TODO: low  endpoint of 95% confidence interval
    public double confidenceLo(){
        double lo=mean()-1.96*stddev()/Math.sqrt(trialstime);
        return lo;
    }

    //TODO: high endpoint of 95% confidence interval
    public double confidenceHi(){
        double hi=mean()+1.96*stddev()/Math.sqrt(trialstime);
        return hi;
    }

    //TODO: test client (described below)
    public static void main(String[] args){
        int gridsize= StdIn.readInt();
        int trialtimes=StdIn.readInt();
        PercolationStats test=new PercolationStats(gridsize,trialtimes);
        System.out.print("mean                    = "+test.mean()+"\n");
        System.out.print("stddev                  = "+test.stddev()+"\n");
        System.out.print("95% confidence interval = ["+test.confidenceLo()+", "+test.confidenceHi()+"]\n");
    }


}
