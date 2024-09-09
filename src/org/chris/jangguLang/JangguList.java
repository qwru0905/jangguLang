package org.chris.jangguLang;

import java.util.*;

public class JangguList {
    private List<Character> charList = new ArrayList<Character>();
    private List<Integer> intList = new ArrayList<Integer>();
    private List<Boolean> isCharList = new ArrayList<Boolean>();

    public JangguList() {
        for (int i = 0; i < 1024; i++) {
            charList.add('0');
            intList.add(0);
            isCharList.add(false);
        }
    }

    public int size() {
        return 1024;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < 1024; i++) {
            Object element = isCharList.get(i)?charList.get(i):intList.get(i);
            if (element.equals(o)) {
                return true;
            }
        }

        return false;
    }

    public Object get(int index) {
        if (index >= 1024 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return isCharList.get(index)?charList.get(index):intList.get(index);
    }

    public boolean isIndexChar(int index) {
        return isCharList.get(index);
    }

    public Object set(int index, Object element) {
        if (index >= 1024 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Object oldElement = isCharList.get(index)?charList.get(index):intList.get(index);
        if (element instanceof Integer) {
            isCharList.set(index, false);
            intList.set(index, (Integer) element);
        } else if (element instanceof Character) {
            isCharList.set(index, true);
            charList.set(index, (Character) element);
        }
        return oldElement;
    }
}
