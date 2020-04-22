package com.hiloj.note.executor;

public class Demo {
    public volatile static int value;
    public static void main(String[] args) {
        int a = 10;
        value = 9;
        value += a;
    }
}
