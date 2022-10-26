package com.auto.model;

import java.util.Random;

public class Page {
    private static Random random = new Random();

    private String title;

    private int column;

    public Page(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = random.nextInt(4);
    }

}
