package test;

public class HeapSort {
	public void sort(long arr[])
	{
		int N = arr.length;
		for (int i = N / 2 - 1; i >= 0; i--)
			heapify(arr, N, i);
		for (int i = N - 1; i > 0; i--) {
			long temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			heapify(arr, i, 0);
		}
	}
	void heapify(long arr[], int N, int i)
	{
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		if (l < N && arr[l] > arr[largest])
			largest = l;

		if (r < N && arr[r] > arr[largest])
			largest = r;

		if (largest != i) {
			long swap = arr[i];
			arr[i] = arr[largest];
			arr[largest] = swap;

			heapify(arr, N, largest);
		}
	}
}
