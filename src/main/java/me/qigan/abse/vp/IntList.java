package me.qigan.abse.vp;

import java.util.*;
import java.util.function.Predicate;

public class IntList extends ArrayList<Integer> {

    public int sum = 0;

    @Override
    public boolean add(Integer integer) {
        this.sum += integer;
        return super.add(integer);
    }

    @Override
    public void add(int index, Integer element) {
        this.sum += element;
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        for (Integer it : c) {
            sum += it;
        }
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Integer> c) {
        for (Integer it : c) {
            sum += it;
        }
        return super.addAll(index, c);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Integer) sum -= (Integer) o;
        return super.remove(o);
    }

    @Override
    public Integer remove(int index) {
        sum -= this.get(index);
        return super.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object it : c) {
            if (it instanceof Integer) sum -= (Integer) it;
        }
        return super.removeAll(c);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        for (int i = fromIndex; i <= toIndex; i++) {
            sum -= this.get(i);
        }
        super.removeRange(fromIndex, toIndex);
    }

    public double medium(double last) {
        return (double) sum + last / (double) this.size() + 1;
    }
}
