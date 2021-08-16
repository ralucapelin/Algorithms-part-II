import java.util.ArrayList;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
class QuickSort {
    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    int partition(ArrayList<String> arr, int low, int high, ArrayList<Integer> tmp) {
        String pivot = arr.get(high);
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (pivot.compareTo(arr.get(j)) > 0) {
                i++;

                // swap arr[i] and arr[j]
                String temp = arr.get(i);
                int t = tmp.get(i);
                tmp.set(i, tmp.get(j));
                arr.set(i, arr.get(j));
                tmp.set(j, t);
                arr.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        String temp = arr.get(i + 1);
        int t = tmp.get(i + 1);
        arr.set(i + 1, arr.get(high));
        tmp.set(i + 1, tmp.get(high));
        arr.set(high, temp);
        tmp.set(high, t);

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(ArrayList<String> arr, int low, int high, ArrayList<Integer> tmp) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high, tmp);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1, tmp);
            sort(arr, pi + 1, high, tmp);
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver program
    public static void main(String args[]) {

    }
}
