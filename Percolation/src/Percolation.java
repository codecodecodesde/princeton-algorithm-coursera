
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] sitestatus;
    private int gridsize;//nxn grid
    private WeightedQuickUnionUF site;
    private WeightedQuickUnionUF siteforFull;
    private int top;
    private int bottom;
    private int num;


    //TODO:create n-by-n grid, with all sites blocked
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        gridsize=n;
        site=new WeightedQuickUnionUF(n*n+2);//be careful!!
        siteforFull=new WeightedQuickUnionUF(n*n+1);
        sitestatus=new boolean[n][n];
        top=0;
        bottom=n*n+1;
        num=0;

    }
    //TODO:open site (row, col) if it is not open already
    public void open(int row, int col){
        if(row<=0||row>gridsize||col<=0||col>gridsize)
            throw new IndexOutOfBoundsException("row or col out of bounds");

        if(!isOpen(row, col)){
            sitestatus[row-1][col-1]=true;
            num++;
            if(row==1){
                site.union(index(row, col),top);
                siteforFull.union(index(row, col),top);
            }
            if(row==gridsize)
                site.union(index(row, col),bottom);

            unionAdjSite(row,col,row+1,col);
            unionAdjSite(row,col,row-1,col);
            unionAdjSite(row,col,row,col+1);
            unionAdjSite(row,col,row,col-1);

        }
    }

    private void unionAdjSite(int row, int col, int adjrow, int adjcol){
        try{
            if(isOpen(adjrow,adjcol)){
            site.union(index(row, col),index(adjrow,adjcol));
            siteforFull.union(index(row, col),index(adjrow,adjcol));}
        }catch(IndexOutOfBoundsException e){
        //
        }
    }


    //TODO:is site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row<=0||row>gridsize||col<=0||col>gridsize)
            throw new IndexOutOfBoundsException("row or col out of bounds");
        return sitestatus[row-1][col-1];
    }


    //TODO: is site (row, col) full? connect to top
    public boolean isFull(int row, int col){
        if(row<=0||row>gridsize||col<=0||col>gridsize)
            throw new IndexOutOfBoundsException("row or col out of bounds");

        if(!isOpen(row, col))
            return false;
        else{
            return siteforFull.connected(index(row, col),top);
        }
    }

    //TODO: number of open sites
    public int numberOfOpenSites(){
        return num;
    }

    //TODO:does the system percolate?
    public boolean percolates() {
        return site.connected(top,bottom);
    }

    private int index(int row, int col){
        int id=(row-1)*gridsize+col;
        return id;
    }

}