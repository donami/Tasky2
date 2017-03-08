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
     * Remove a node at a specific position
     * @param pos   The position of the node to be removed
     * @return True if the removal succeded else false
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
     * Get the number of elements in the list
     * @return  Number of elements
     */
    public int getNrOfElements() {
        return this.nrOfElements;
    }

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
    protected Node getFirst() {
        return this.first;
    }

    /**
     * Setter of first node
     * @param node  Node to be first in list
     */
    protected void setFirst(Node node) {
        this.first = node;
    }

    /**
     * Setter of the last node
     * @param node  Node to be last in the list
     */
    protected void setLast(Node node) {
        this.last = node;
    }
}
