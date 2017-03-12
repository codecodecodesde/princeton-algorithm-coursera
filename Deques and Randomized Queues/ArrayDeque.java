

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/**
 * Created by wangxuan on 17/2/12.
 */
public class ArrayDeque<Item> implements Iterable<Item> {
    private Item[] a;
    private int first;
    private int last;
    private int size;
    //private int cap;

    /*construct an empty Arraydeque*/
    public ArrayDeque() {
        a = (Item[]) new Object[4];
        first = 1;
        last = first+1;
        size=0;
    }

    //resize array
    private void resize(int newLength) {
        Item[] copy=(Item[]) new Object[newLength];
        int start=newLength/4;
        int i = first+1;
        for(int n=0; n<size; n++) {
            if(i==a.length)
                i=0;
            copy[start] = a[i];
            i++;
            start++;
        }
        first=start-size-1;
        if(first<0)
            first=1;
        last=start;
        a = copy;
    }

    //is the deque empty
    public boolean isEmpty() {
        return size == 0;
    }

    //return the number of items in the deque
    public int size() {
        return size;
    }

    //add the item to the front
    public void addFirst(Item item) {
        if(item==null)
            throw new java.lang.NullPointerException();
        if(size==a.length)
            resize(2*a.length);
        a[first] = item;
        size++;
        first--;
        if(first<0)
            first=a.length-1;

    }

    //remove and return the item from the front
    public void addLast(Item item) {
        if(item==null)
            throw new java.lang.NullPointerException();
        if(size==a.length)
            resize(2*a.length);
        a[last] = item;
        size++;
        last++;
        if(last>(a.length-1))
            last=0;
    }

    //remove and return the item from the end
    public Item removeFirst(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        Item item;
        first++;
        if(first==a.length)
            first=0;
        item = a[first];
        a[first]= null;
        size--;
        if(size<(a.length/4))
            resize(a.length/2);
        return item;
    }

    public Item removeLast(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        Item item;
        last--;
        if(last<0)
            last=a.length-1;
        item = a[last];
        a[last]= null;
        size--;
        if(size<(a.length/4))
            resize(a.length/2);
        return item;
    }

    //return an iterator over items in order from front to end

    public Iterator<Item> iterator() {
        return  new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i=first+1;
        private int N=0;
        public boolean hasNext() {
            N++;
            if(N<=size)
                return true;
            return false;
        }
        public  Item next(){
            Item item;
            if(i==a.length)
                i=0;
            item=a[i];
            i++;
            if(item==null)
                throw new java.util.NoSuchElementException();
            return item;
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }

    }

    public static void main(String[] arg){
        ArrayDeque<String> l = new ArrayDeque<String>();
        l.addLast("A");

        StdOut.print(l.removeFirst());
        l.addLast("B");
         StdOut.print(l.removeFirst());

        l.addFirst("A");
        l.addLast("C");
        l.addFirst("M");
        l.addLast("N");
        l.addFirst("j");




        for(String s:l)
            StdOut.println(s);

        StdOut.print(l.size());
    }



}
