package com.tasky.app;

import com.tasky.app.models.Category;
import com.tasky.util.Encrypt;
import com.tasky.util.SLList;

import java.io.*;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by markus on 2017-03-20.
 */
public class CategoryHandler extends Observable {
    private final App app;
    private final SLList<Category> list;

    public CategoryHandler(App app) {
        this.app = app;
        this.list = new SLList<>();
    }

    /**
     * Add category to the list
     * @param category  Category to add
     */
    public void add(Category category) {
        this.list.add(category);

        setChanged();
        notifyObservers();

        this.writeListToFile();
    }

    /**
     * Remove category from list
     * @param index Index to remove
     */
    public void remove(int index) {
        this.list.remove(index + 1);

        setChanged();
        notifyObservers();
    }

    public SLList<Category> getList() {
        return this.list;
    }

    private String getFilename() {
        return Encrypt.encrypt(this.app.getAuth().getUsername()) + "_categories.ser";
    }

    /**
     * Write the current list to file
     */
    private void writeListToFile() {
        // Write the tasks to the files
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(this.getFilename()));

            Iterator<Category> listIterator = this.list.iterator();
            while (listIterator.hasNext()) {
                os.writeObject(listIterator.next());
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load tasks from file
     */
    void loadFromFile() throws Exception {
        File file = new File(this.getFilename());
        System.out.println("1");
        if (file.exists() && !file.isDirectory()) {
            // Load the tasks from file to the list
            System.out.println("2");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(this.getFilename()));
                while (true) {
                    Category category = (Category) ois.readObject();
                    System.out.println(category);
                    this.list.add(category);
                }
            } catch (EOFException e) {

            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        }
    }

}
