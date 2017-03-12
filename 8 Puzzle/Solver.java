import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

/**
 * Created by wangxuan on 17/3/12.
 */
public class Solver {

    private class SearchNode implements Comparable<SearchNode>{
        public final Board currentBoard;
        public final SearchNode preSearchNode;
        public final int num_move;
        private final int priority;

        public SearchNode(Board currentBoard, int num_move, SearchNode preSearchNode){
            this.currentBoard=currentBoard;
            this.num_move=num_move;
            this.preSearchNode=preSearchNode;
            this.priority=num_move+currentBoard.manhattan();
        }
        //write compareTo() function to compare value of keys

        public int compareTo(SearchNode that){
            if(this.priority < that.priority)
                return -1;
            else if(this.priority > that.priority)
                return 1;
            else
                return 0;
        }
    }

    private SearchNode goalNode;
    private boolean solvable;

    /*
        find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial){
        if(initial==null)
            throw new java.lang.NullPointerException();
        solvable=false;
        int nmove=0;
        MinPQ<SearchNode> PQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        PQ.insert(new SearchNode(initial,0,null));
        twinPQ.insert(new SearchNode(initial.twin(),0,null));


        //before any one of SN or twinSN dequeued Board isn't the goal Board
        while(true){
            SearchNode MinSN = PQ.delMin();
            SearchNode MintwinSN = twinPQ.delMin();
            if(MinSN.currentBoard.isGoal()){
                solvable=true;
                goalNode=MinSN;
                break;
            }
            if(MintwinSN.currentBoard.isGoal())break;

            Iterable<Board> neighbor = MinSN.currentBoard.neighbors();
            Iterable<Board> twinneighbor = MintwinSN.currentBoard.neighbors();
            for(Board b:neighbor){
                if(MinSN.preSearchNode!=null&&b.equals(MinSN.preSearchNode.currentBoard))
                    continue;
                PQ.insert(new SearchNode(b,MinSN.num_move+1,MinSN));
            }
            for(Board twinb:twinneighbor)
            {
                if(MintwinSN.preSearchNode!=null&&twinb.equals(MintwinSN.preSearchNode.currentBoard))
                    continue;
                twinPQ.insert(new SearchNode(twinb,MintwinSN.num_move+1,MintwinSN));
            }
        }

    }

    /*
        is the initial board solvable?
     */
    public boolean isSolvable(){
        return solvable;
    }

    /*
        min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves(){
        if(!isSolvable())
            return -1;
        return goalNode.num_move;
    }
    /*
        sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution(){
        if(!isSolvable())
            return null;
        SearchNode pre=goalNode;
        Stack<Board> solution= new Stack<>();
        solution.push(goalNode.currentBoard);
        for(int i=0;i<this.moves();i++){
            pre=pre.preSearchNode;
            solution.push(pre.currentBoard);
        }
        return solution;

    }

    /*
        solve a slider puzzle (given below)
     */
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}
