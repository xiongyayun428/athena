package com.xiongyayun.athena.core.utils;

import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SafeDateTest
 *
 * @author: Yayun.Xiong
 * @date: 2020/1/19
 */
public class SafeDateTest extends TestCase {
	@Autowired private SafeDate safeDate;

	public void testGetCurrent() {
		System.out.println(SafeDate.getCurrentDate());
		System.out.println(SafeDate.getCurrentDateTime());
		System.out.println(SafeDate.getCurrentDay());
		System.out.println(SafeDate.getCurrentMonth());
		System.out.println(SafeDate.getCurrentTime());
		System.out.println(SafeDate.getCurrentYear());
	}
}
