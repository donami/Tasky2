package com.tasky.ui.views;

import com.tasky.app.CategoryHandler;
import com.tasky.app.models.Category;
import com.tasky.ui.IView;
import com.tasky.util.BaseKeyListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Created by markus on 2017-03-21.
 */
public class CategoryList extends JPanel implements IView {
    private Category noCategorySelected;
    private DefaultListModel<Category> listModel;
    private JList<Category> jList;
    private final CategoryHandler categoryHandler;
    private JScrollPane jScrollPane;

    CategoryList(CategoryHandler categoryHandler) {
        this.categoryHandler = categoryHandler;

        this.setLayout(new MigLayout());

        this.initComponents();
        this.createGUI();
        this.addEvents();
    }

    /**
     * Initialize components
     */
    public void initComponents() {
        this.noCategorySelected = new Category("No category");
        this.listModel = new DefaultListModel<>();
        this.listModel.addElement(this.noCategorySelected);
        this.jList = new JList<>();
        this.jList.setModel(this.listModel);
        this.jScrollPane = new JScrollPane(this.jList);
    }

    /**
     * Create GUI
     */
    public void createGUI() {
        this.add(this.jScrollPane, "h 100%, w 100%, span, wrap");
    }

    /**
     * Add events
     */
    private void addEvents() {
        this.jList.addKeyListener(new BaseKeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DELETE:
                        if (!isDefaultCategorySelected()) {
                            categoryHandler.remove(jList.getSelectedIndex());
                        }
                        break;
                    default:
                }
            }
        });
    }

    /**
     * Add a category
     * @param categoryName  Name of category
     */
    void addCategory(String categoryName) {
        Category category = new Category(categoryName);
        this.categoryHandler.add(category);
    }

    /**
     * Refresh the category list
     */
    void refresh() {
        this.listModel.clear();
        this.listModel.addElement(this.noCategorySelected);

        Iterator<Category> listIterator = this.categoryHandler.getList().iterator();
        while (listIterator.hasNext()) {
            this.listModel.addElement(listIterator.next());
        }
    }

    /**
     * Getter for the JList
     * @return  The JList
     */
    JList<Category> getCategoryJList() {
        return this.jList;
    }

    /**
     * Determine if the default category is selected
     * @return  If default value is selected return true
     */
    boolean isDefaultCategorySelected() {
        return this.jList.getSelectedValue().equals(this.noCategorySelected);
    }

    /**
     * Get the selected value of the JList
     * @return  Selected value
     */
    Category getSelectedValue() {
        return this.jList.getSelectedValue();
    }

    /**
     * If the JList contains a specific category
     * @param category  The category
     * @return  True if it contains the category
     */
    boolean contains(Category category) {
        return this.jList.getNextMatch(category.getTitle(), 0, Position.Bias.Forward) > -1;
    }

    /**
     * Set focus on the list and set index
     * to the default category
     */
    void focus() {
        this.jList.setSelectedIndex(0);
        this.jList.requestFocusInWindow();
    }
}
