// JangguList.java

package org.chris.jangguLang;

import java.util.*;

public class JangguList {
    private final List<Character> charList = new ArrayList<>();
    private final List<Integer> intList = new ArrayList<>();
    private final List<Boolean> isCharList = new ArrayList<>();

    public JangguList() {
        // 초기 상태 설정 (빈 상태로 시작)
    }

    public int size() {
        return isCharList.size();
    }

    public boolean contains(Object o) {
        for (int i = 0; i < isCharList.size(); i++) {
            Object element = isCharList.get(i) ? charList.get(i) : intList.get(i);
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public Object get(int index) {
        checkAndExpandSize(index);
        return isCharList.get(index) ? charList.get(index) : intList.get(index);
    }

    public boolean isIndexChar(int index) {
        checkAndExpandSize(index);
        return isCharList.get(index);
    }

    public Object set(int index, Object element) {
        checkAndExpandSize(index);
        Object oldElement = isCharList.get(index) ? charList.get(index) : intList.get(index);
        if (element instanceof Integer) {
            isCharList.set(index, false);
            intList.set(index, (Integer) element);
            charList.set(index, '0'); // 문자 리스트 초기화
        } else if (element instanceof Character) {
            isCharList.set(index, true);
            charList.set(index, (Character) element);
            intList.set(index, 0); // 정수 리스트 초기화
        }
        return oldElement;
    }

    private void checkAndExpandSize(int index) {
        while (index >= isCharList.size()) {
            charList.add('0');
            intList.add(0);
            isCharList.add(false);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < isCharList.size(); i++) {
            if (isCharList.get(i)) {
                sb.append(charList.get(i));
            } else {
                sb.append(intList.get(i));
            }
            if (i < isCharList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
