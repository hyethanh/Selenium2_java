package com.auto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Page {
    private String name;
    private Page parent;
    private int column;
    private String displayAfter;
    private boolean isPublic;
    private static final Page overviewPage = new Page("Overview", null, 2, "", false);

    public Page(String name) {
        this.name = name;
        this.parent = null;
        this.column = 2;
        this.displayAfter = overviewPage.name;
        this.isPublic = false;
    }

    public Page(String name, Page parent) {
        this.name = name;
        this.parent = parent;
        this.column = 2;
        this.displayAfter = "";
        this.isPublic = false;
    }

    public Page(String name, String displayAfter) {
        this.name = name;
        this.parent = null;
        this.column = 2;
        this.displayAfter = displayAfter;
        this.isPublic = false;
    }


    public static Page overviewPage() {
        return Page.overviewPage;
    }
}
