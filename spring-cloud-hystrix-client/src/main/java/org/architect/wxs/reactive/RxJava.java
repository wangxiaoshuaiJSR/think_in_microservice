package org.architect.wxs.reactive;

import rx.Observer;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.Random;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/2
 * @javadoc ：
 */
public class RxJava {
    public static void main(String[] args) {
        Single.just("hello,world")
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("执行结束");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("熔断保护");
                    }

                    @Override
                    public void onNext(String s) {
                        Random random = new Random();
                        int value = random.nextInt(200);
                        System.out.println(value);
                        if(value>100){
                            throw new RuntimeException("超时了！！！");
                        }
                    }
                });
    }
}
