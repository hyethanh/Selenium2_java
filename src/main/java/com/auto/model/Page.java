package com.auto.model;

import java.util.Random;

public class Page {
    private static Random random = new Random();

    private String title;

    private int column;

    private ChildPage childPage;

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        this.title = title;
        return title;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = random.nextInt(4);
    }

    public ChildPage getChildPage() {
        return childPage;
    }

    public void setChildPage(ChildPage childPage) {
        this.childPage = childPage;
    }
}
