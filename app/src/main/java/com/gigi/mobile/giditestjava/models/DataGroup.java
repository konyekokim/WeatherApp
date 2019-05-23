package com.gigi.mobile.giditestjava.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGroup {
    private List<List<Data>> dataGroup;

    @SafeVarargs
    public DataGroup (List<Data>... args) {
        this.dataGroup = new ArrayList<>();
        this.dataGroup.addAll(Arrays.asList(args));
    }

    public DataGroup (List<List<Data>> group) {
        this.dataGroup = new ArrayList<>(group);
    }

    public void addData(List<Data> data) {
        if (dataGroup == null)
            dataGroup = new ArrayList<>();
        dataGroup.add(data);
    }

    public List<List<Data>> getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(List<List<Data>> dataGroup) {
        this.dataGroup = dataGroup;
    }

    @Override
    public String toString() {
        return "DataGroup{" +
                "dataGroup=" + dataGroup +
                '}';
    }
}
