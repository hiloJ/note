package com.hiloj.note.atomic;

import java.util.concurrent.atomic.*;

/**
 *  原子类
 */
public class AtomicMethod {
    public static void main(String[] args) {
        atomicMethod1();
        atomicMethod2();
        atomicMethod3();
        atomicMethod4();

    }

    private static void atomicMethod4() {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.add(11);
    }

    private static void atomicMethod3() {
        AtomicStampedReference<String> reference = new AtomicStampedReference<>("index", 1);

        System.out.println(reference.compareAndSet("index", "hello", 1, 1));

        int[] stampHolder = new int[10];
        String s = reference.get(stampHolder);
        System.out.println(s);
        System.out.println(stampHolder[0]);
    }

    private static void atomicMethod2() {
        AtomicIntegerFieldUpdater integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class,"age");
        Person person = new Person("zhangsan", 10);
        System.out.println(person);
        integerFieldUpdater.set(person,20);
        System.out.println(person);
    }

    private static void atomicMethod1() {
        System.out.println("=========== 无参构造 ===========");
        System.out.println("AtomicBoolean--" + new AtomicBoolean().toString());
        System.out.println("AtomicInteger--" + new AtomicInteger().toString());
        System.out.println("AtomicLong--" + new AtomicLong().toString());
        System.out.println("AtomicReference--" + new AtomicReference<>().toString());

        // 有参构造
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        AtomicInteger atomicInteger = new AtomicInteger(666);
        AtomicLong atomicLong = new AtomicLong(3L);
        AtomicReference<Object> objectAtomicReference = new AtomicReference<>("hello atomic");

        System.out.println("=========== 获取当前值get() ===========");
        System.out.println("AtomicBoolean--" + atomicBoolean.get());
        System.out.println("AtomicInteger--" + atomicInteger.get());
        System.out.println("AtomicLong--" + atomicLong.get());
        System.out.println("AtomicReference--" + objectAtomicReference.get());

        System.out.println("=========== 设置当前值set() ===========");
        atomicBoolean.set(false);
        atomicInteger.set(100);
        atomicLong.set(1000L);
        objectAtomicReference.set("world");
        System.out.println("=========== 获取set()后的值 ===========");
        System.out.println("AtomicBoolean--" + atomicBoolean.toString());
        System.out.println("AtomicInteger--" + atomicInteger.toString());
        System.out.println("AtomicLong--" + atomicLong.toString());
        System.out.println("AtomicReference--" + objectAtomicReference.toString());

        System.out.println("=========== 设置当前值lazySet() ===========");
        atomicBoolean.lazySet(false);
        atomicInteger.lazySet(333);
        atomicLong.lazySet(111L);
        objectAtomicReference.lazySet("lazySet");
        System.out.println("=========== 获取lazySet()后的值 ===========");
        System.out.println("AtomicBoolean--" + atomicBoolean.toString());
        System.out.println("AtomicInteger--" + atomicInteger.toString());
        System.out.println("AtomicLong--" + atomicLong.toString());
        System.out.println("AtomicReference--" + objectAtomicReference.toString());

        AtomicIntegerArray integerArray = new AtomicIntegerArray(5);
        System.out.println(integerArray.getAndIncrement(0));
        System.out.println(integerArray.getAndAdd(0,6));
        System.out.println(integerArray.incrementAndGet(0));
        System.out.println(integerArray.get(0));
    }

    static class Person{
        private String name;
        private int age;
        public Person(String name, int age){
            this.name = name;
            this.age = age;
        }
    }
}
