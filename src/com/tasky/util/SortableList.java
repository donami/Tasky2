package com.tasky.util;

import java.util.Comparator;

/**
 * Created by markus on 2017-03-08.
 */
public class SortableList<T extends Comparable<T>> extends SLList<T> {
    public SortableList() {}

    public void sort(Comparator<? super T> c) {
        Node i = this.getFirst().getNext();
        Node prevI = this.getFirst();

        while(i != null){
            Node j = this.getFirst();

            if (c.compare(j.getData(), i.getData()) > 0){
                prevI.setNext(i.getNext());
                i.setNext(j);
                this.setFirst(i);
            }
            else {
                Node prevJ = null;
                while (j.getNext() != null){
                    prevJ = j;
                    j = j.getNext();
                    if (c.compare(j.getData(), i.getData()) > 0){
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
        this.setLast(prevI);
    }

}
