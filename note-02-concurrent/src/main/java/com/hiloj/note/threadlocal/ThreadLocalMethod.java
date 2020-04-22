package com.hiloj.note.threadlocal;

import java.util.ArrayList;

public class ThreadLocalMethod {
    public static void main(String[] args) {
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        // true
        System.out.println(objectThreadLocal.get() == null);
        ThreadLocal<ArrayList<Object>> threadLocal = ThreadLocal.withInitial(ArrayList::new);
        // false
        System.out.println(threadLocal.get() == null);
    }
}
