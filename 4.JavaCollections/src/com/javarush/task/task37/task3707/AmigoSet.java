package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Serializable,Cloneable, Set {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<E, Object>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
        //this.map = new HashMap<E, Object>(Integer.max(16, (int) (collection.size() / .75f)));
        addAll(collection);
    }
    @Override
    public Iterator iterator() {
        Iterator<E> iterator = map.keySet().iterator();
        return iterator;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean add(Object o) {
        int startsize = map.size();
        map.put((E) o, PRESENT);

        if (map.size() > startsize) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o, map.get(o));
    }

    @Override
    public Object clone() {
        try {
            AmigoSet copy = (AmigoSet) super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        output.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        output.writeInt(map.size());
        for (Object object : map.keySet()) {
            output.writeObject(object);
        }
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        int capacity = input.readInt();
        float loadFactor = input.readFloat();
        map = new HashMap(capacity, loadFactor);
        int size = input.readInt();
        for (int i = 0; i < size; i++) {
            E e = (E) input.readObject();
            map.put(e, PRESENT);
        }
    }
}
