import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by wangxuan on 17/3/7.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> OriginSegment;
    private ArrayList<Point> startPoint;
    private ArrayList<Point> endPoint;
    private ArrayList<Double> SlopeArray;
    private int NumOfLineSegment;


    public FastCollinearPoints(Point[] points){
        checkCornerCase(points);

        NumOfLineSegment=0;
        OriginSegment = new ArrayList<>();
        SlopeArray = new ArrayList<>();
        startPoint = new ArrayList<>();
        endPoint = new ArrayList<>();

        Point[] pointscopy = new Point[points.length];
        System.arraycopy(points,0,pointscopy,0,points.length);

        if(points.length>1){

            for(int i=0;i<points.length;i++){
                Arrays.sort(pointscopy, points[i].slopeOrder());

                double slopeOld=pointscopy[0].slopeTo(pointscopy[1]);
                double slopeNew;
                int NumOfPoint;
                int index;

                for(int j=2;j<pointscopy.length;j++) {
                    slopeNew=pointscopy[0].slopeTo(pointscopy[j]);

                    if (slopeNew == slopeOld) {
                        NumOfPoint = 3;
                        index = j - 1;
                        while ((j + 1) < pointscopy.length && pointscopy[0].slopeTo(pointscopy[++j]) == slopeOld) {
                            NumOfPoint = NumOfPoint + 1;
                        }

                        if(NumOfPoint>=4){
                            Point[] tmp= new Point[NumOfPoint];
                            tmp[0]=pointscopy[0];
                            for(int n=1;n<NumOfPoint;n++){
                                tmp[n]=pointscopy[index++];
                            }
                            Arrays.sort(tmp);

                            int m;
                            for(m=0;m<NumOfLineSegment;m++){
                                if(SlopeArray.get(m)==slopeOld){
                                    if(startPoint.get(m).compareTo(tmp[0])==0
                                            &&endPoint.get(m).compareTo(tmp[NumOfPoint-1])==0)
                                        break;
                                    else if(startPoint.get(m).compareTo(tmp[0])==0){
                                        if(endPoint.get(m).compareTo(tmp[NumOfPoint-1])<0){
                                            endPoint.set(m,tmp[NumOfPoint-1]);
                                            OriginSegment.set(m,new LineSegment(tmp[0],tmp[NumOfPoint-1]));
                                            break;
                                        }
                                        else
                                            break;
                                    }
                                    else if(endPoint.get(m).compareTo(tmp[NumOfPoint-1])==0){
                                        if(startPoint.get(m).compareTo(tmp[0])>0){
                                            startPoint.set(m,tmp[0]);
                                            OriginSegment.set(m,new LineSegment(tmp[0], tmp[NumOfPoint-1]));
                                            break;
                                        }
                                        else
                                            break;
                                    }
                                    else
                                        continue;

                                }
                            }

                            if(m==NumOfLineSegment){
                                SlopeArray.add(slopeOld);
                                startPoint.add(tmp[0]);
                                endPoint.add(tmp[NumOfPoint-1]);
                                OriginSegment.add(new LineSegment(tmp[0],tmp[NumOfPoint-1]));
                                NumOfLineSegment++;
                            }
                        }
                    }

                    else
                        slopeOld =slopeNew;
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
                    throw new java.lang.IllegalArgumentException("Duplicated Points");
            }
        }
    }

    public  int numberOfSegments(){
        return NumOfLineSegment;
    }


    public LineSegment[] segments(){
        LineSegment[] segments = new LineSegment[NumOfLineSegment];
        OriginSegment.toArray(segments);
        //OriginSegment=null;
        return segments;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();

        FastCollinearPoints f = new FastCollinearPoints(points);

            for (LineSegment p : f.segments()) {
                StdOut.println(p);
                //p.draw();
            }

        //StdDraw.show();
        System.out.println(f.numberOfSegments());
    }


}
