package com.wmz.utils.bean;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/14
 * @Author: wmz
 * @Description:
 */
public class MyViewMode extends ViewModel {

    private MutableLiveData<List<Contact>> users;

    public LiveData<List<Contact>> getUsers(){
        if (users == null){
            users = new MutableLiveData<>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers(){
        String[] names = {"张三","李四","王五","John","小明","Leo","Wang","Li","Ha","Yun"};
        List<Contact> userList = new ArrayList<>();
        for (int i = 0; i < names.length; i++){
            Contact user = new Contact();
            user.name=names[i];
            userList.add(user);
        }
        users.setValue(userList);
    }

    public void setUsers(String name){
        List<Contact> userList = new ArrayList<>();
        Contact user = new Contact();
        user.name=name;
        userList.add(user);
        users.setValue(userList);
    }


}
