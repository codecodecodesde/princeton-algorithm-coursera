
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wangxuan on 17/3/7.
 */
public class BruteCollinearPoints {
    private LineSegment[] tmp;
    private int Number;
    private Point[] pointscopy;
    /*finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        checkCornerCase(points);
        tmp = new LineSegment[points.length];//?
        Number=0;
        pointscopy= new Point[points.length];
        System.arraycopy(points,0,pointscopy,0,points.length);
        Arrays.sort(pointscopy);
        //find all line segments containing 4 points
        for(int i=0; i<pointscopy.length-3;i++){
            for(int j=i+1;j<pointscopy.length-2;j++){
                for(int k=j+1;k<pointscopy.length-1;k++){
                    for(int l=k+1;l<pointscopy.length;l++){
                        if((pointscopy[i].slopeTo(pointscopy[j])==pointscopy[i].slopeTo(pointscopy[k]))
                                &&(pointscopy[i].slopeTo(pointscopy[k])==pointscopy[i].slopeTo(pointscopy[l]))){
                             tmp[Number++]=new LineSegment(pointscopy[i],pointscopy[l]);
                        }
                    }
                }
            }
        }

    }

    private void checkCornerCase(Point[] points){
        for(int i=0;i<points.length-1;i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }

    /*
    the number of line segments
     */
    public int numberOfSegments(){
        return Number;
    }
    /*
    the line segments
     */
    public LineSegment[] segments(){
        LineSegment[] allSegments = new LineSegment[numberOfSegments()];
        System.arraycopy(tmp,0,allSegments,0,numberOfSegments());
        return allSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
