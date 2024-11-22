
public class Heap {
	
	Thing arr[];
	int size=0;
	public Heap(int n)
	{
		arr=new Thing[n];
		
	}
	public class Thing
	{
		String word;
		int importance;
		public Thing(String word,int importance)
		{
			this.word=word;
			this.importance=importance;
		}

	}
	public static void PercolateDown(Thing A[], int n, int i) {
	    Thing temp = A[i]; // Store the element to be moved down
	    int j;
	    while (2 * i + 1 < n) { // Note: 0-based indexing
	        // Find min child
	        j = 2 * i + 1; // Left child
	        if (j + 1 < n && A[j + 1].importance < A[j].importance) {
	            j++; // Right child is smaller
	        }
	        // Move the smaller child up if it violates the min-heap
	        if (temp.importance > A[j].importance) {
	            A[i] = A[j];
	            i = j;
	        } else {
	            break; // No more violations
	        }
	    }
	    A[i] = temp; // Place the original element in its correct position
	}

	public static void BuildHeap(Thing A[], int n) {
		for (int i = n / 2; i > 0; i--)
		PercolateDown(A, n, i);
		}
	public void findKLargest(int k)
	{
		BuildHeap(this.arr,k);
		for(int i=k;i<this.arr.length;i++)
		{
			if(arr[i].importance>arr[0].importance)
			{
				arr[0]=arr[i];
				PercolateDown(arr,k,0);
			}
		}
	}
	
	public boolean isEmpty()
	{
		return this.arr[0]==null;
	}
	public static void main(String[] args) {
        // Create a heap object with capacity 10
        Heap heap = new Heap(10);
        
        // Populate the heap's array with sample data
        heap.arr = new Heap.Thing[] {
            heap.new Thing("A", 3),
            heap.new Thing("B", 1),
            heap.new Thing("C", 6),
            heap.new Thing("D", 5),
            heap.new Thing("E", 9),
            heap.new Thing("F", 8),
            heap.new Thing("G", 7),
            heap.new Thing("H", 4),
            heap.new Thing("I", 10),
            heap.new Thing("J", 2)
        };
        
        // Set the size of the heap (number of elements)
        heap.size = heap.arr.length;
        
        // Find the 3 largest elements
        int k = 3;
        heap.findKLargest(k);
        
        // Print the k-largest elements
        System.out.println("The " + k + "-largest elements are:");
        for (int i = 0; i < k; i++) {
            System.out.println("Word: " + heap.arr[i].word + ", Importance: " + heap.arr[i].importance);
        }
    }
}
