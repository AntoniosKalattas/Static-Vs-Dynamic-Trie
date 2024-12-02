
public class Heap {
	
	Thing arr[];
	public Heap(int k)
	{
		arr=new Thing[k+1];
		this.arr[0]=new Thing("CurrentSize",0);
		
		
		for(int i=1;i<=k;i++)
		{
			this.arr[i]=new Thing("",-1);
		}

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
	    while (2 * i <= n) { // 1-based indexing
	        j = 2 * i; // Left child
	        if (j + 1 <= n && A[j + 1].importance < A[j].importance) {
	            j++; // Right child is smaller
	        }
	        if (temp.importance > A[j].importance) {
	            A[i] = A[j];
	            i = j;
	        } else {
	            break;
	        }
	    }
	    A[i] = temp; // Place the original element in its correct position
	}


	public static void BuildHeap(Thing A[], int n) {
		for (int i = n / 2; i > 0; i--)
		PercolateDown(A, n, i);
		}
	
	
	
	
		public void displayHeapAsTree() {
			if (this.arr[0].importance == 0) {
				System.out.println("Heap is empty.");
				return;
			}
			int n = this.arr[0].importance;
			int height = (int) (Math.log(n) / Math.log(2)) + 1; // Calculate tree height
		
			// Determine the maximum length of a node's text representation
			int maxNodeWidth = 0;
			for (int i = 1; i <= n; i++) {
				String nodeText = arr[i].word + "(" + arr[i].importance + ")";
				maxNodeWidth = Math.max(maxNodeWidth, nodeText.length());
			}
		
			// Tree width per level
			int maxWidth = (int) Math.pow(2, height - 1) * maxNodeWidth * 2;
		
			for (int level = 0; level < height; level++) {
				int nodesInLevel = (int) Math.pow(2, level);
				int spaceBetween = maxWidth / nodesInLevel - maxNodeWidth;
		
				// Print leading spaces
				System.out.print(" ".repeat(spaceBetween / 2));
		
				// Print nodes in the current level
				for (int i = 0; i < nodesInLevel; i++) {
					int index = (int) Math.pow(2, level) + i;
					if (index > n) break;
		
					String nodeText = arr[index].word + "(" + arr[index].importance + ")";
					System.out.print(nodeText);
		
					// Space between nodes
					if (i < nodesInLevel - 1) {
						System.out.print(" ".repeat(spaceBetween));
					}
				}
				System.out.println(); // Move to the next level
		
				// Print branches if not the last level
				if (level < height - 1) {
					int branchSpacing = spaceBetween / 2 - 1;
					int branches = nodesInLevel * 2;
		
					// Print leading spaces for branches
					System.out.print(" ".repeat(branchSpacing + maxNodeWidth / 2));
					for (int i = 0; i < branches; i++) {
						System.out.print(i % 2 == 0 ? "/" : "\\");
						if (i < branches - 1) {
							System.out.print(" ".repeat(branchSpacing * 2 + maxNodeWidth));
						}
					}
					System.out.println();
				}
			}
		}
		
		public boolean stringExists(String word) {
		    for (int i = 1; i <= this.arr[0].importance; i++) { // Start from index 1
		        if (arr[i].word.equals(word)) {
		            return true;
		        }
		    }
		    return false;
		}
	public void insert(Thing thing) {
		
		if(stringExists(thing.word))
			return;
		
		if (this.arr[0].importance<this.arr.length-1)
		{
			
		int index = this.arr[0].importance + 1;
		while (index > 1 && this.arr[(index / 2)].importance >thing.importance) {
		this.arr[index].importance=this.arr[(index / 2)].importance;
		this.arr[index].word=this.arr[(index / 2)].word;

		index = index / 2;
		}
		this.arr[index]=thing;
		this.arr[0].importance++;
		
		}
		
		else if(this.arr[0].importance==arr.length-1) 
		{
			if(thing.importance>arr[1].importance)
			{
				arr[1]=thing;
				PercolateDown(this.arr,this.arr[0].importance,1);
				
			}
		}
		
	
	}
		
	
	public boolean isEmpty()
	{
		return this.arr[0]==null;
	}
	public static void main(String[] args) {
	    // Initialize the Heap with capacity for 5 elements
	    int k = 5;
	    Heap heap = new Heap(k);

	    // Step 2: Insert 50 non-random words with varying importance
	    String[] words = {
	        "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew",
	        "IndianFig", "Jackfruit", "Kiwi", "Lemon", "Mango", "Nectarine", "Orange",
	        "Papaya", "Quince", "Raspberry", "Strawberry", "Tangerine", "UgliFruit",
	        "Vanilla", "Watermelon", "Xigua", "YellowPassionFruit", "Zucchini",
	        "Apricot", "Blackberry", "Cranberry", "Dragonfruit", "Eggfruit", "Feijoa",
	        "Guava", "Hackberry", "IceCreamBean", "Jujube", "Kumquat", "Lychee",
	        "Mulberry", "Nance", "Olive", "Peach", "Quandong", "Rambutan", "Salak",
	        "Tamarind", "Umbu", "Voavanga", "WaxApple", "YamBean", "Ziziphus"
	    };

	    // Assign importance values (simple pattern for testing)
	    for (int i = 0; i < 50; i++) {
	        heap.insert(heap.new Thing(words[i],i + 1)); // Importance = index + 1
	    }

	    // Step 3: Display the contents of the heap
	    System.out.println("Heap contents after 50 insertions:");
	}

}
