import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.lang.*;
import java.util.NoSuchElementException;

/**
 * Created by wangxuan on 17/2/12.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int size;
    private int pos;

    //construct an empty randomized queue
    public RandomizedQueue(){
        a =(Item[]) new Object[2];
        size=0;
        pos=0;
    }
    //is the queue empty?
    public boolean isEmpty(){ return size==0;}

    //return the number of items on the queue
    public int size(){ return size; }

    //resize array
    private void resize(int newLength){
        Item[] copy=(Item[]) new Object[newLength];
        System.arraycopy(a,0,copy,0,size);
        a=copy;
    }

    //add the item
    public void enqueue(Item item){
        if(item==null)
            throw  new java.lang.NullPointerException();
        if(pos==a.length){
           resize(2*a.length);
        }
        a[pos]=item;
        pos++;
        size++;
    }

    //remove and return a random item
    public Item dequeue(){
        if(size==0)
            throw new java.util.NoSuchElementException();
        if(size<a.length/4)
            resize(a.length/2);
        int RandomIndex = StdRandom.uniform(0,size);
        Item item=a[RandomIndex];
        a[RandomIndex]=a[size-1];
        a[size-1]=null;
        pos=size-1;
        size--;
        return item;
    }

    //return (but do not remove) a random item
    public Item sample(){
        if(size==0)
            throw new java.util.NoSuchElementException();
        int RandomIndex= StdRandom.uniform(0,size);
        return a[RandomIndex];
    }

    public Iterator<Item> iterator(){
        return  new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{
        private Item[] IteratorArray = (Item[]) new Object[size];
        private int current=0;
        private RandomizedQueueIterator(){
            System.arraycopy(a,0,IteratorArray,0,size);
            StdRandom.shuffle(IteratorArray);
        }

        public boolean hasNext(){
            return current<size;
        }

        public Item next(){
            Item item=IteratorArray[current];
            if(!hasNext())
                throw new NoSuchElementException();//?
            current++;
            return item;
        }

        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] arg){
        RandomizedQueue<String> l=new RandomizedQueue<String>();
        l.enqueue("xuan");
        l.enqueue("huhu");
        l.enqueue("wang");
        l.enqueue("ji");
        l.enqueue("mm");
        l.dequeue();
        l.dequeue();
        l.dequeue();


        for(String s:l)
            StdOut.println(s);

        StdOut.print(l.size());
    }


}
