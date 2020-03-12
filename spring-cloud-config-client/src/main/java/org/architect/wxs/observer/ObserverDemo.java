package org.architect.wxs.observer;

import java.util.*;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：java观察者模式
 */
public class ObserverDemo {
    public static void main(String[] args) {
        MyObservable observable = new MyObservable();

        //增加订阅
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        observable.setChanged();
        //发布通知，订阅者是被动感知（推的莫斯）
        observable.notifyObservers("hello,world");

        echoIterator();
    }

    public static void echoIterator(){
        List<Integer> values = Arrays.asList(1,2,3,4,5,6);
        Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()){//通过循环主动去取，这个是拉的模式
            System.out.println(iterator.next());
        }
    }

    public static class MyObservable extends Observable{
        public synchronized void setChanged(){
            super.setChanged();
        }
    }
}
