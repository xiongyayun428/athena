package com.xiongyayun.athena.service.id.util;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * IdUtilTest
 *
 * @author: Yayun.Xiong
 * @date: 2020/1/22
 */
public class IdUtilTest {
	public static void main(String[] args) throws InterruptedException {
		Set<Long> idSet = Sets.newConcurrentHashSet();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 50000; i++) {
				idSet.add(IdUtil.nextId());
			}
		});
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 50000; i++) {
				idSet.add(IdUtil.nextId());
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("生成id完毕");
		System.out.println(idSet.size());
	}
}
