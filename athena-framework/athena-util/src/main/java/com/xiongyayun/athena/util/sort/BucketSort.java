package com.xiongyayun.athena.util.sort;

import java.util.Arrays;

/**
 * 桶排序
 *
 *
 * @author: Yayun.Xiong
 * @date: 2019/12/16
 */
public class BucketSort implements IArraySort {
	private static final InsertSort insertSort = new InsertSort();

	@Override
	public int[] sort(int[] sourceArray) throws Exception {
		// 对 arr 进行拷贝，不改变参数内容
		int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
		// 设置桶的默认数量为5
		return bucketSort(arr, 5);
	}

	private int[] bucketSort(int[] arr, int bucketSize) throws Exception {
		if (arr.length == 0) {
			return arr;
		}
		// 输入数据的最小值
		int minValue = arr[0];
		// 输入数据的最大值
		int maxValue = arr[0];
		for (int value : arr) {
			if (value < minValue) {
				minValue = value;
			} else if (value > maxValue) {
				maxValue = value;
			}
		}

		int bucketCount = (int) Math.floor((maxValue - minValue) / bucketSize) + 1;
		int[][] buckets = new int[bucketCount][0];

		// 利用映射函数将数据分配到各个桶中
		for (int i = 0; i < arr.length; i++) {
			int index = (int) Math.floor((arr[i] - minValue) / bucketSize);
			buckets[index] = arrAppend(buckets[index], arr[i]);
		}

		int arrIndex = 0;
		for (int[] bucket : buckets) {
			if (bucket.length <= 0) {
				continue;
			}
			// 对每个桶进行排序，这里使用了插入排序
			bucket = insertSort.sort(bucket);
			for (int value : bucket) {
				arr[arrIndex++] = value;
			}
		}

		return arr;
	}

	/**
	 * 自动扩容，并保存数据
	 *
	 * @param arr
	 * @param value
	 */
	private int[] arrAppend(int[] arr, int value) {
		arr = Arrays.copyOf(arr, arr.length + 1);
		arr[arr.length - 1] = value;
		return arr;
	}
}
