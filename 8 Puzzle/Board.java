
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.util.Arrays;
import java.util.Stack;

/**
 * Created by wangxuan on 17/3/11.
 */
public class Board {
    private final char[] a;
    private int manhattan;
    private final int dimension;

    /*
    construct a board from n-by-n array of blocks
     */
    public Board(int[][] blocks){
        int n=0;
        dimension=blocks.length;
        int x,y;
        a = new char[blocks.length*blocks.length];
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                a[n]=(char)blocks[i][j];
                if (a[n] != n + 1&&a[n]!=0) {
                    x =(a[n]-1) / dimension;
                    y = (a[n]-1) % dimension;
                    manhattan = manhattan + Math.abs(x - i) + Math.abs(y - j);
                }
                n++;

            }
        }

    }


    public int dimension(){
        return dimension;
    }

    /*
        number of blocks out of place
     */
    public int hamming(){
        int h=0;
        for(int i=0;i<a.length-1;i++){
            if(a[i] != i+1)
                h++;
        }
        return h;
    }
    /*
        sum of Manhattan distances between blocks and goal
     */
    public int manhattan(){
        return manhattan;
    }

//    private void traceOfmh(int originpos,int newpos){
//        int x1,y1,x2,y2;
//        x1=originpos/dimension;
//        y1=originpos%dimension;
//        x2=newpos/dimension;
//        y2=newpos%dimension;
//
//        manhattan=manhattan+(x2-x1)+(y2-y1);
//    }
    /*
        is this board the goal board?
     */
    public boolean isGoal(){
       return this.manhattan()==0;
    }

    /*
        a board that is obtained by exchanging any pair of blocks
     */
    public Board twin(){
        int tmp=0;
        int[][] twinblocks = new int[dimension][dimension];

        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                twinblocks[i][j]=a[tmp++];
            }
        }

        int index1=StdRandom.uniform(0,a.length);
        int index2=StdRandom.uniform(0,a.length);
        while(a[index1] == 0)
            index1=StdRandom.uniform(0,a.length);
        while((index1==index2)||(a[index2]==0))
            index2=StdRandom.uniform(0,a.length);
        int x1,y1,x2,y2;
        x1=index1/dimension;
        y1=index1%dimension;
        x2=index2/dimension;
        y2=index2%dimension;
        tmp=twinblocks[x1][y1];
        twinblocks[x1][y1]=twinblocks[x2][y2];
        twinblocks[x2][y2]=tmp;
        return  new Board(twinblocks);
    }

    /*
        does this board equally?
     */
    public boolean equals(Object y){
        if(this==y) return true;
        if( y==null ) return false;
        if(!(y instanceof Board)) return false;
        Board that = (Board) y;
        if(Arrays.equals(this.a,that.a))
            return true;
        else
            return false;
    }

    /*
        all neighboring boards
     */
    public Iterable<Board> neighbors(){
        Stack<Board> s = new Stack<>();
        int blankID=0;
        for(int i=0;i<a.length;i++){
            if(a[i]==0){
                blankID=i;
                break;
            }
        }
        int dimension =this.dimension();
        int rowid = blankID/dimension;
        int colid=blankID%dimension;

        //exist item to the right of blank
        if(colid != dimension-1) {
            s.add(new Board(exchange(blankID + 1, blankID)));
            //traceOfmh(blankID+1,blankID);
        }
        //exist item to the left of blank
        if(colid != 0){
            s.add(new Board(exchange(blankID-1,blankID)));
            //traceOfmh(blankID-1,blankID);
        }
        //exist item below blank
        if(rowid != dimension-1){
            s.add(new Board(exchange(blankID+dimension,blankID)));
            //traceOfmh(blankID+dimension,blankID);
        }
        //exist item above blank
        if(rowid != 0) {
            s.add(new Board(exchange(blankID - dimension, blankID)));
            //traceOfmh(blankID-dimension,blankID);
        }
        return s;
    }


    private int[][] exchange( int i, int j){
        int[][] blockcopy = new int[dimension][dimension];
        int tmp=0;
        for(int x=0;x<dimension;x++){
            for(int y=0;y<dimension;y++){
                blockcopy[x][y]=a[tmp++];
            }
        }
        int x1,y1,x2,y2;
        x1=i/dimension;
        y1=i%dimension;
        x2=j/dimension;
        y2=j%dimension;
        tmp = blockcopy[x1][y1];
        blockcopy[x1][y1]=blockcopy[x2][y2];
        blockcopy[x2][y2]=tmp;
        return blockcopy;
    }

    /*
        string representation of this board
     */
    public String toString(){
        int dimension=this.dimension();
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0,count=0; i < a.length;i++) {
                s.append(String.format("%2d ",(int) a[i]));
                count++;
                if(count == dimension){
                    s.append("\n");
                    count=0;
                }
        }
        return s.toString();
    }

    /*
        unit test
     */
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                blocks[i][j]=in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.manhattan());
        StdOut.println(initial);
        Iterable<Board> s=initial.neighbors();
        for(Board b:s){
            StdOut.println(b.manhattan());
            StdOut.println(b);
        }
    }
}

