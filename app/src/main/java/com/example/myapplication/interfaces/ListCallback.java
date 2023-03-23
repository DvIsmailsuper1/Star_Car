package com.example.myapplication.interfaces;

import java.util.ArrayList;

public interface ListCallback<Model> {
    void onSuccess(ArrayList<Model> list);

    void onFailure();
}
