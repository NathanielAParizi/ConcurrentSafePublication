package com.example.concurrentsafepublication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity<T> extends AppCompatActivity {

    // The purpose of this code is to demonstrate the safe publication of data shared between
    // threads.

    private Object lock = new Object();
    private T dropBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Safely publish an object to another thread.
        //@param obj, The caller will hold no other references to this specific object.

        publish(dropBox);

    }

    private void publish(T obj) {

        synchronized (lock){
            if(null != dropBox){

                throw new IllegalStateException("The drop box is full!");

            }
            dropBox = obj;
        }
    }

    public T receive(){
        synchronized (lock){
            T obj = dropBox;
            dropBox = null;
            return obj;
        }
    }
}
