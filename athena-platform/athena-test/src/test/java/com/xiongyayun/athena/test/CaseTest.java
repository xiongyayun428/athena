package com.xiongyayun.athena.test;

import junit.framework.TestCase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RsaTest
 *
 * @author 熊亚运
 * @date 2019-06-17
 */
@Slf4j
public class CaseTest extends TestCase {
	final static ReentrantLock reentrantLock = new ReentrantLock();

//	private static volatile int restCount = 100;
	private static AtomicInteger restCount = new AtomicInteger(100);

	public static void main(String[] args) {
		Runnable r = () -> {
			while (restCount.get() > 0) {
				System.out.println(Thread.currentThread().getName() + " 卖出一张票，还剩" + restCount.decrementAndGet() + "张票");
			}
		};
		Thread t1 = new Thread(r, "Thread - 1");
		Thread t2 = new Thread(r, "Thread - 2");
		Thread t3 = new Thread(r, "Thread - 3");
		Thread t4 = new Thread(r, "Thread - 4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

//	public void testThread1() {
//		Runnable r = () -> {
//			while (CaseTest.restCount > 0) {
//				System.out.println(Thread.currentThread().getName() + " 卖出一张票，还剩" + CaseTest.restCount + "张票");
//			}
//		};
//		Thread t1 = new Thread(r, "Thread - 1");
//		Thread t2 = new Thread(r, "Thread - 2");
//		Thread t3 = new Thread(r, "Thread - 3");
//		Thread t4 = new Thread(r, "Thread - 4");
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//	}

	public void testAQS() {
		Thread t1 = new Thread(() -> testSync());
		t1.setName("T1");
		t1.start();
	}

	public static void testSync() {
		reentrantLock.lock();
		System.out.println(Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("--------------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("-------unlock-------------");
			reentrantLock.unlock();
		}
	}




	public void testThread() {
		Thread t1 = new Thread(new TestThread());
		t1.setName("T1");
		Thread t2 = new Thread(new TestThread());
		t2.setName("T2");
		System.out.println("t1---1-->" + t1.getState());
		System.out.println("t2---1-->" + t2.getState());
		try {
			t1.start();
			t2.start();

			Thread.sleep(1000);
			System.out.println("t1---2-->" + t1.getState());
			System.out.println("t2---2-->" + t2.getState());
//			Thread.sleep(500);
//			t.`wait`();
//			t.join();
//			new Lock().tryLock()
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("t1======>" + t1.getState());
		System.out.println("t2======>" + t2.getState());
//		System.exit(0);
	}


    public void testLeetCode() {
		String pattern = "abba";
		String str = "dog cat cat dog";

//		System.out.println(wordPattern(pattern, str));

//		int[] nums = new int[]{1,7,2, 4,8,3,5,6};
//		int[] nums = new int[]{1,7,1 ,2 , 3,8,5,5,4};
////		int[] nums = new int[]{1, 7, 3, 6, 5, 6};
//		System.out.println(pivotIndex(nums));
		int oldCapacity = 2;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		System.out.println(newCapacity);
    }

//	public boolean wordPattern(String pattern, String str) {
//		String[] s = str.split(" "); //以空格分隔str
//		if(s.length != pattern.length()) return false; //如果没有全部成对的映射则返回false
//		Map<Character, String> map = new HashMap<>(); //存放映射
//		for(int i = 0; i < pattern.length(); i++){
//			if(!map.containsKey(pattern.charAt(i))){ //1. 没有映射时执行
//				if(map.containsValue(s[i])) return false; //2. 没有映射的情况下s[i]已被使用，则不匹配返回false
//				map.put(pattern.charAt(i), s[i]); //3. 构建映射
//			}else{
//				if(!map.get(pattern.charAt(i)).equals(s[i])) return false; //当前字符串与映射不匹配,返回false
//			}
//		}
//		return true;
//	}

//	public boolean wordPattern(String pattern, String str) {
//		if ("" == pattern || "" == str) {
//			return pattern == str;
//		}
//		String[] arr = str.split(" ");
//		if (arr.length != pattern.length()) {
//			return false;
//		}
//		HashMap<Character, String> map = new HashMap<Character,String>();
//		for (int i=0; i<pattern.length(); i++) {
//			char ch = pattern.charAt(i);
//			if (map.containsKey(ch)) {
//				if (!map.get(ch).equals(arr[i])) {
//					return false;
//				}
//			} else {
//				if (i>=1 && pattern.charAt(i) != pattern.charAt(i-1) && arr[i].equals(arr[i-1])) {
//					return false;
//				}
//				map.put(ch, arr[i]);
//			}
//		}
//		return true;
//	}

	/*
	 * 使用HashMap映射 pattern中字符(key)和str中单词(value)的关系：
	 * 	如果pattern和str的长度不同，直接返回false
	 * 	如果当前key存在于map，但是value值不等于当前的单词，返回false；
	 * 	如果当前key不存在于map，但是当前单词存在于map的value中，返回false
	 */
	public static boolean wordPattern(String pattern, String str) {
		String[] s = str.split(" ");
		if(pattern.length() != s.length) return false; //两个长度不等，直接返回false
		Map<Character,String> map = new HashMap<>();
		for(int i = 0; i < pattern.length(); i++) {
			if(!map.containsKey(pattern.charAt(i))){ //如果map的Key中不包含pattern中的字符
				if(map.containsValue(s[i])) { //但是Value中包含s中的单词
					return false;
				}
				map.put(pattern.charAt(i), s[i]); //map中都不包含，则将映射关系存入map中
			}else { //如果map的Key中包含pattern中的字符
				if(!map.get(pattern.charAt(i)).equals(s[i])) { //但是pattern中字符对应的Value即映射的单词不等于当前的单词
					return false;
				}
			}
		}
		return true;
	}

	public int pivotIndex(int[] nums) {
		if (nums.length == 0 || nums == null) {
			return -1;
		}
		int sum = 0;
		for (int x : nums) {
			sum += x;
		}
		int leftsum = 0;
		int rightsum = 0;
		for (int i = 0; i < nums.length; i++) {
			rightsum = sum - nums[i] - leftsum;
			if (leftsum == rightsum) {
				return i;
			}
			leftsum = leftsum + nums[i];//这里一定是先判断结束后再加和左边，因为每次判断的值左边和的值，不包含当前元素的值。
		}
		return -1;
	}
}


class TestThread implements Runnable {

	@Override
	public void run() {
		System.out.println("ThreadA "+Thread.currentThread().getName()+" run ->" + Thread.currentThread().getState());
//			try {
////				Thread.sleep(10000);
//				Thread.currentThread().wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		commonResource();
	}

	public static synchronized void commonResource() {
		while(true) {
			// Infinite loop to mimic heavy processing
			// 't1' won't leave this method
			// when 't2' try to enters this
//			System.out.print("1");
		}
	}
}
