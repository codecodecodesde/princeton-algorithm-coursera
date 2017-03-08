import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/**
 * Created by wangxuan on 17/2/18.
 */
public class LinkedListDeque<Item> implements Iterable<Item> {

    private  int size;
    private Node sentinel;

    private class Node{
        Item item;
        Node next;
        Node pre;

        private Node(Item i, Node p, Node n){
            item=i;
            pre=p;
            next=n;
        }
    }

    //to construct an empty Deque
    public LinkedListDeque (){
        size=0;
        sentinel = new Node(null,null,null);
    }//generic

    //is the Deque empty
    public boolean isEmpty(){ return size==0; }

    // return the number of items on the deque
    public int size(){ return size; }

    // add the item to the front
    public void addFirst(Item item){
        if(item==null)
            throw new java.lang.NullPointerException();
        size++;
        if(size==1){
            sentinel.next=new Node(item,sentinel,sentinel);
            sentinel.pre=sentinel.next;
        }
        else{
            sentinel.next=new Node(item,sentinel,sentinel.next);
            sentinel.next.next.pre=sentinel.next;
        }
    }

    // add the item to the end
    public void addLast(Item item){
        if(item==null)
            throw new java.lang.NullPointerException();
        size++;
        if(size==1){
            sentinel.next=new Node(item,sentinel,sentinel);
            sentinel.pre=sentinel.next;
        }
        else{
            sentinel.pre=new Node(item,sentinel.pre,sentinel);
            sentinel.pre.pre.next=sentinel.pre;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        size--;
        if(size<0)
            throw new java.util.NoSuchElementException();
        Item item=sentinel.next.item;
        sentinel.next.item=null;
        if(size==0){
            sentinel.next=null;
            sentinel.pre=null;
        }
        else{
            sentinel.next=sentinel.next.next;
            sentinel.next.pre=sentinel;
        }
        return item;
    }

    //remove and return the item from the end
    public Item removeLast(){
        size--;
        if(size<0)
            throw new java.util.NoSuchElementException();
        Item item=sentinel.pre.item;
        sentinel.pre.item=null;
        if(size==0){
            sentinel.next=null;
            sentinel.pre=null;
        }
        else{
            sentinel.pre=sentinel.pre.pre;
            sentinel.pre.next=sentinel;
        }
        return item;
    }



    public Iterator<Item> iterator(){
        return  new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {

        private Node current=sentinel.next;
        private int N=0;

        public boolean hasNext(){
            N++;
            if(N <= size)
                return true;
            return false;
        }

        public Item next(){
            Item item;
            item=current.item;
            if(item==null)
                throw new java.util.NoSuchElementException();
            current=current.next;
            return item;
        }

        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] arg){
        LinkedListDeque<String> l=new LinkedListDeque<String> ();
        l.addFirst("First");
        l.addLast("Second");
        StdOut.print(l.removeLast());



        for(String s:l)
            StdOut.println(s);

        StdOut.print(l.size());
        StdOut.print();
    }

}
