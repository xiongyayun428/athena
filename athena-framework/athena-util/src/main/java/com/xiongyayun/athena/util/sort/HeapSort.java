package com.xiongyayun.athena.util.sort;

import java.util.Arrays;

/**
 * 堆排序(最小堆/最大堆)
 * <p>适合处理数据量大的序列
 *
 * <p>堆排序（Heapsort）是指利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。堆排序可以说是一种利用堆的概念来排序的选择排序。分为两种方法：
 * <ol>
 *     <li>大顶堆：每个节点的值都大于或等于其子节点的值，在堆排序算法中用于升序排列；</li>
 *     <li>小顶堆：每个节点的值都小于或等于其子节点的值，在堆排序算法中用于降序排列；</li>
 * </ol>
 * <p>堆排序的平均时间复杂度为 Ο(nlogn)
 * <p>排序的总比较次数为 O(nlog2n)
 * @see <a href="https://sort.hust.cc/7.heapsort">其他语言实现方式</a>
 *
 * @author Yayun.Xiong
 * @date 2019/12/16
 */
public class HeapSort implements IArraySort {
	@Override
	public int[] sort(int[] sourceArray) throws Exception {
		// 对 arr 进行拷贝，不改变参数内容
		int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
		int len = arr.length;
		buildMaxHeap(arr, len);
		for (int i = len - 1; i > 0; i--) {
			swap(arr, 0, i);
			len--;
			heapify(arr, 0, len);
		}
		return arr;
	}

	/**
	 * 建立大顶堆
	 * @param arr
	 * @param len
	 */
	private void buildMaxHeap(int[] arr, int len) {
		for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
			heapify(arr, i, len);
		}
	}

	/**
	 * 堆调整
	 * @param arr
	 * @param i
	 * @param len
	 */
	private void heapify(int[] arr, int i, int len) {
		// 左节点
		int left = 2 * i + 1;
		// 右节点
		int right = 2 * i + 2;
		// 三个元素中最大元素的位置
		int largest = i;

		if (left < len && arr[left] > arr[largest]) {
			largest = left;
		}

		if (right < len && arr[right] > arr[largest]) {
			largest = right;
		}

		if (largest != i) {
			swap(arr, i, largest);
			heapify(arr, largest, len);
		}
	}

	private void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
