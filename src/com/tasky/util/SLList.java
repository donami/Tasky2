package com.tasky.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by markus on 2017-03-08.
 */
public class SLList<T extends Comparable<T>> implements IList<T> {

    private Node first;
    private Node last;

    private int nrOfElements;

    public SLList() {
        this.first = null;
        this.nrOfElements = 0;
        this.last = null;
    }

    /**
     * Add a new node to the list at the last position
     * @param data The data to be added
     */
    public void add(T data) {
        if (this.nrOfElements == 0) {
            this.addToEmptyList(data);
        }
        else {
            Node walker = this.first;
            while (walker.next != null) {
                walker = walker.next;
            }
            this.last = walker.next = new Node(data);
            this.nrOfElements++;
        }
    }

    /**
     * Add a new node at specified position
     * @param data  The data to be added
     * @param pos   The position to be inserted at, starting at 1
     */
    public void add(T data, int pos) {
        if (pos < 1) {
            throw new IllegalArgumentException("Position in list can not be less than 1");
        }
        else if (this.nrOfElements == 0) {
            this.addToEmptyList(data);
        }
        else if (pos == 1) {
            this.first = new Node(data, this.first);
            this.nrOfElements++;
        }
        else {
            Node walker = this.first;

            for (int i = 1; i < pos - 1 && walker.next != null; i++) {
                walker = walker.next;
            }

            if (pos >= this.nrOfElements) {
                walker.next = this.last = new Node(data, walker.next);
            }
            else {
                walker.next = new Node(data, walker.next);
            }

            this.nrOfElements++;
        }
    }

    /**
     * Get a node at a specific position
     * @param pos   The position of the requested element
     * @return  The element
     */
    public T get(int pos) {
        if (pos > nrOfElements) {
            throw new IllegalArgumentException("Position out of range");
        }

        // Get the first element
        if (pos == 1 || pos == 0) {
            return this.first.getData();
        }

        Node walker = this.first;
        for (int i = 0; i < pos - 1; i++) {
            walker = walker.next;
        }

        return walker.getData();
    }

    /**
     * Check if the list contains an item
     * @param item  The item to search for
     * @return  True if it contains the item, else false
     */
    public boolean contains(T item) {
        Iterator<T> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item))
                return true;
        }

        return false;
    }

    /**
     * Update a node at specific position
     * @param pos   The position of the node to be changed
     * @param data  New element to take its place
     * @return  True if action was successful
     */
    public boolean update(int pos, T data) {
        if (pos > nrOfElements) {
            throw new IllegalArgumentException("Position out of range");
        }

        // If element is the first
        if (pos == 1 || pos == 0) {
            this.first.setData(data);
        }

        Node walker = this.first;
        for (int i = 0; i < pos - 1; i++) {
            walker = walker.next;
        }

        // Update data
        walker.setData(data);

        return true;
    }

    /**
     * Remove a node at a specific position
     * @param pos   The position of the node to be removed
     * @return True if the removal succeeded else false
     */
    public boolean remove(int pos) {
        if (pos > nrOfElements) {
            return false;
        }

        // Remove the first element
        if (pos == 1 || pos == 0) {
            if (this.first == this.last) {
                this.last = null;
            }

            this.first = this.first.next;
            this.nrOfElements--;

            return true;
        }

        Node walker = this.first;
        for (int i = 1; i < pos - 1; i++) {
            walker = walker.next;
        }

        if (walker.next == this.last) {
            this.last = walker;
        }

        walker.next = walker.next.next;
        this.nrOfElements--;

        return true;
    }

    /**
     * Remove all items from list
     */
    public void clear() {
        this.first = null;
        this.last = null;
        this.nrOfElements = 0;
    }

    /**
     * Get the number of elements in the list
     * @return  Number of elements
     */
    public int getNrOfElements() {
        return this.nrOfElements;
    }

    /**
     * Add element to empty list
     * @param data  The element
     */
    private void addToEmptyList(T data) {
        this.first = this.last = new Node(data);
        this.nrOfElements++;
    }

    /**
     * Print the list as a string
     * @return String representation of the list
     */
    public String toString() {
        String toReturn = "";

        Node walker = this.first;
        while(walker != null) {
            toReturn += walker.data + "\t";
            walker = walker.next;
        }

        return toReturn;
    }

    /**
     * Find the index of an item
     * @param item  The item to search for
     * @return  The index if found, otherwise -1
     */
    public int getIndexOfItem(T item) {
        int index = -1;

        Iterator<T> iterator = this.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (item == iterator.next()) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Iterator for looping through the list
     * @return  Iterator
     */
    public Iterator<T> iterator() {
        if (this.nrOfElements == 0) {
            return Collections.<T>emptyList().iterator();
        }
        return new Iterator<T>() {
            private Node walker = null;

            @Override
            public boolean hasNext() {
                return walker != last;
            }

            @Override
            public T next() {
                if (this.walker == null) {
                    this.walker = first;
                    return this.walker.getData();
                }

                if (this.walker.getNext() == null) {
                    throw new NoSuchElementException();
                }

                this.walker = this.walker.getNext();
                return this.walker.getData();
            }
        };
    }

    protected class Node {
        private Node next;
        private T data;

        Node(T data) {
            this.data = data;
            this.next = null;
        }

        Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * Get the first node in list
     * @return First node
     */
    Node getFirst() {
        return this.first;
    }

    /**
     * Setter of first node
     * @param node  Node to be first in list
     */
    void setFirst(Node node) {
        this.first = node;
    }

    /**
     * Setter of the last node
     * @param node  Node to be last in the list
     */
    void setLast(Node node) {
        this.last = node;
    }
}
