package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    private ArrayList<Entry<String>> leafs = new ArrayList<>();
    private ArrayList<Entry<String>> leafsToRemove = new ArrayList<>();
    private int countLines;

    public CustomTree() {
        this.root = new Entry<>("0");
        this.root.lineNumber = 0;
        this.root.parent = null;
        leafs.add(root);
    }


    @Override
    public boolean add(String s) {
        Entry<String> element = new Entry<>(s);
        element.root = root;
        for (int i = 0; i < leafs.size(); i++) {
            Entry<String> leaf = leafs.get(i);
            leaf.checkChildren();
            if (!leaf.isAvailableToAddChildren()) {
                continue;
            }
            if (leaf.availableToAddLeftChildren) {
                element.parent = leaf;
                leaf.leftChild = element;
                element.lineNumber = element.parent.lineNumber + 1;
                leafs.add(element);
            } else if (leaf.availableToAddRightChildren) {
                element.parent = leaf;
                leaf.rightChild = element;
                element.lineNumber = element.parent.lineNumber + 1;
                leafs.add(element);
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return leafs.size() - 1;
    }

    public String getParent(String s) {
        for (Entry<String> leaf : leafs) {
            if (s.equals(leaf.elementName)) {
                return leaf.parent.elementName;
            }
        }
        return null;
    }

    @Override
    public boolean remove(Object var1) {
        if (var1 instanceof String) {
            String name = (String) var1;
            for (int i = 0; i < leafs.size(); i++) {
                Entry<String> leaf = leafs.get(i);
                if (name.equals(leaf.elementName)) {
                    addLeafsToRemove(leaf);
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
        leafs.removeAll(leafsToRemove);
        return true;
    }

    private void addLeafsToRemove(Entry<String> leaf) {
        if (leaf == null) return;
        if (leaf.equals(leaf.parent.leftChild)) {
            leaf.parent.leftChild = null;
        } else {
            leaf.parent.availableToAddLeftChildren = true;
            leaf.parent.availableToAddRightChildren = true;
            leaf.parent.rightChild = null;
        }
        leafsToRemove.add(leaf);
        addLeafsToRemove(leaf.leftChild);
        addLeafsToRemove(leaf.rightChild);
    }

    @Override
    public String get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends String> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super String> consumer) {

    }


    @Override
    public Stream<String> stream() {
        return null;
    }

    @Override
    public Stream<String> parallelStream() {
        return null;
    }


    @Override
    public boolean removeIf(Predicate<? super String> predicate) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<String> unaryOperator) {

    }

    @Override
    public void sort(Comparator<? super String> comparator) {

    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;
        Entry<String> root;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public void checkChildren() {
            if (leftChild != null) {
                availableToAddLeftChildren = false;
            }
            if (rightChild != null) {
                availableToAddRightChildren = false;
            }
        }


        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
