package com.xiongyayun.athena.util.sort;

/**
 * IArraySort
 *
 * @author Yayun.Xiong
 * @date 2019/12/16
 */
@FunctionalInterface
public interface IArraySort {

	/**
	 * 数组排序
	 * @param sourceArray	未排序数组
	 * @return				已排序数组
	 * @throws Exception
	 */
	int[] sort(int[] sourceArray) throws Exception;
}
