package me.qigan.abse.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Using for set relative of object
 *
 * @param <T>
 * @param <O>
 */

public class AddressedData<T, O> {
    private T namespace;
    private O object;

    public AddressedData(T namespace, O object) {
        this.namespace = namespace;
        this.object = object;
    }

    public AddressedData() {}

    public T getNamespace() {
        return namespace;
    }

    public O getObject() {
        return object;
    }

    public void setNamespace(T namespace) {
        this.namespace = namespace;
    }

    public void setObject(O object) {
        this.object = object;
    }

    public static <T, O> Map<T, O> toMap(List<AddressedData<T, O>> lst) {
        Map<T, O> map = new HashMap<T, O>();
        for (AddressedData<T, O> data: lst) {
            map.put(data.getNamespace(), data.getObject());
        }
        return map;
    }

    public static <T, O> List<AddressedData<T, O>> fromMap(Map<T, O> map) {
        List<AddressedData<T, O>> list = new ArrayList<>();
        for (Map.Entry<T, O> mpe : map.entrySet()) {
            list.add(new AddressedData<>(mpe.getKey(), mpe.getValue()));
        }
        return list;
    }


}
