package com.hiloj.note.executor;

import java.util.concurrent.Executor;

public class ExecutorMethod1 {
    public static void main(String[] args) {
        Executor executor = command -> new Thread(command).start();
        executor.execute(()->System.out.println("execute"));
    }
}
