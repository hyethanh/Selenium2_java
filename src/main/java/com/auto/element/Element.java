package com.auto.element;

import com.logigear.element.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

public class Element extends com.logigear.element.Element {

    public Element(String locator) {
        super(locator);
    }

    public Element(By by) {
        super(by);
    }

    public Element(String locator, Object... args) {
        super(locator, args);
    }

    public Element(BaseElement parent, String locator) {
        super(parent, locator);
    }

    public Element(BaseElement parent, By by) {
        super(parent, by);
    }

    public Element(BaseElement parent, String locator, Object... args) {
        super(parent, locator, args);
    }

    public void stalenessOf() {
        try{
            waitForVisible();
        } catch (StaleElementReferenceException e) {
            System.out.printf(e.getMessage());
            element();
        }
    }

    public void click() {
        stalenessOf();
        element.click();
    }

    @Override
    public void enter(CharSequence... value) {
        stalenessOf();
        super.enter(value);
    }

    @Override
    public void hover() {
        stalenessOf();
        super.hover();
    }
}
