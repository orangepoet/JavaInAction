package cn.orangepoet.inaction.algorithm;

/**
 * Created by Orange on 16/8/25.
 */
public class SortAndSearch {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 2, 1, 4, 7, 6};
//        quickSort(arr, 0, arr.length - 1);
//        System.out.println(arr);
        mergeSort(arr, 0, arr.length - 1);
        int index = binarySearch(3, arr);
        System.out.println(index);
        System.out.println(arr);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private static int binarySearch(int i, int[] arr) {
        int low = 0;
        int height = arr.length - 1;
        while (low <= height) {
            int mid = (low + height) / 2;
            if (i < arr[mid])
                height = mid - 1;
            else if (i > arr[mid])
                low = mid + 1;
            else
                return mid;
        }
        return -1;
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (right > left) {
            int mid = (right + left) >>> 1;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] L = new int[mid - left + 1];
        int[] R = new int[right - mid];

        for (int i = 0; i < L.length; i++) {
            L[i] = arr[left + i];
        }

        for (int i = 0; i < R.length; i++) {
            R[i] = arr[mid + 1 + i];
        }

        int l = 0;
        int r = 0;
        int k = left;
        while (k <= right && l < L.length && r < R.length) {
            if (L[l] < R[r])
                arr[k++] = L[l++];
            else
                arr[k++] = R[r++];
        }

        while (l < L.length && k <= right) {
            arr[k++] = L[l++];
        }

        while (r < R.length && k <= right) {
            arr[k++] = R[r++];
        }
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (right > left) {
            int p = getPartition(arr, left, right);
            quickSort(arr, left, p - 1);
            quickSort(arr, p + 1, right);
        }
    }

    private static int getPartition(int[] arr, int left, int right) {
        int l = left;
        int r = right;
        int p = l;
        while (l < r) {
            while (l < r && arr[r] >= arr[p])
                r--;

            swap(arr, p, r);
            p = r;

            while (l < r && arr[l] <= arr[p])
                l++;

            swap(arr, p, l);
            p = l;
        }

        return p;
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }
}
