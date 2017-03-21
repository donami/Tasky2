package com.tasky.util;

/**
 * Created by markus on 2017-03-08.
 */
interface IList<T> {
    void add(T data);
    void add(T data, int pos);
    T get(int pos);
    boolean remove(int pos);
    int getNrOfElements();
}
