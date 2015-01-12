package com.ly.cc.fragment.custcollect.android5p0.recyclerView;

import java.util.ArrayList;

/**
 * Created by kongbei on 2015/1/11.
 */
public class SampleModelDao {
    public static ArrayList<SampleModel> getSampleData(int size) {
        ArrayList<SampleModel> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new SampleModel("新建项目 <" + i + "> "));
        }
        return list;
    }
}
