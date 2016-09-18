package com.lv.databingdemo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;

/**
 * User: 吕勇
 * Date: 2016-09-18
 * Time: 13:08
 * Description:
 */
public class Employee extends BaseObservable{
    private String fristName;
    private String lastName;

    public ObservableMap<String,String> mMap=new ObservableArrayMap<>();

    public Employee() {
    }

    public Employee(String fristName, String lastName) {
        this.fristName = fristName;
        this.lastName = lastName;
        mMap.put("key1","val1");
        mMap.put("key2","val2");
        mMap.put("key3","val3");
    }

    @Bindable
    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
        notifyPropertyChanged(com.lv.databingdemo.BR.fristName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(com.lv.databingdemo.BR.lastName);
    }
}
