package test;

import java.util.ArrayList;

public class BucketSort {
    private long MAX = Long.MAX_VALUE;
    private long arr[];
    private int n;

    BucketSort(long[] arr)
    {
        this.arr = arr;
        n = this.arr.length;
        sort(this.arr);
    }
    private void sort(long[] arr){
        n = arr.length;
        
        if (n <= 0)
			return;

		ArrayList<ArrayList<Long>> buckets = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			buckets.add(new ArrayList<Long>());
		}
        
		for (int i = 0; i < n; i++) {
			int key = (int) ((arr[i] * n)/MAX);
			buckets.get((int) key).add(arr[i]);
		}

		for (int i = 0; i < n; i++) {
			buckets.get(i).sort(null);
		}

		int arrayKey = 0;
		for (int i = 0; i < n; i++) {
		    int size = buckets.get(i).size();
			for (int j = 0; j < size; j++) {
				arr[arrayKey++] = buckets.get(i).get(j);
			}
		}
    }
}
