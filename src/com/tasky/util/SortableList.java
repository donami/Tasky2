package com.tasky.util;


/**
 * Created by markus on 2017-03-08.
 */
public class SortableList<T extends Comparable<T>> extends SLList<T> {
    public SortableList() {
    }

    public void sort() {
        Node i = this.getFirst().getNext();
        Node prevI = this.getFirst();

        while(i != null){
            Node j = this.getFirst();

            if(j.getData().compareTo(i.getData()) > 0){
                prevI.setNext(i.getNext());
                i.setNext(j);
                this.setFirst(i);
            }
            else{
                Node prevJ = null;
                while (j.getNext() != null){
                    prevJ = j;
                    j = j.getNext();
                    if (j.getData().compareTo(i.getData()) > 0){
                        prevI.setNext(i.getNext());
                        i.setNext(j);
                        prevJ.setNext(i);
                        break;
                    }
                }
            }
            prevI = i;
            i = i.getNext();
        }
    }


}
