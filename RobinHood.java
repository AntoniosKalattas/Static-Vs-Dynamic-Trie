
public class RobinHood {
	RobinHoodNode table[];
	public  int capacity;
	public  int maxProbeLength=5;
	int size;
	public RobinHood(int capacity)
	{
		this.capacity=capacity;
		table=new RobinHoodNode[capacity];
		this.size=0;
	}
	public class RobinHoodNode 
	{
		public int psl;
		public int key;
		
	public RobinHoodNode(int key)
	{
		this.key=key;
		this.psl=0;
	}
	public void setPsl(int psl)
	{
		this.psl=psl;
	}
	public int getKey(int i)
	{
		return table[i].key;
	}
	public int getPsl(int i)
	{
		return table[i].psl;
	}
	}
	public void insert(int key) {
	    if (size >= capacity * 0.9) {
	        rehash();
	    }

	    int position = key % capacity;
	    RobinHoodNode node = new RobinHoodNode(key);
	    
	    
	    if (this.table[position] == null) {
	        node.psl = 0;
	        table[position] = node;
	        size++;
	        return;
	    }
	    int currentPos =position;
	    node.psl = 0;

	    for (int probeLength = 0; probeLength < maxProbeLength; probeLength++) {
	        currentPos = (position + probeLength) % capacity;
	        
	        if (table[currentPos] == null) {
	            table[currentPos] = node;
	            size++;
	            return;
	        }

	        if (node.psl > table[currentPos].psl) {
	            RobinHoodNode temp = table[currentPos];
	            table[currentPos] = node;
	            node = temp;
	        }
	        
	        node.psl++;
	    }
	}
	public boolean search(int key)
	{
		int position=key%capacity;
		for(int pbLen=0;pbLen<maxProbeLength;pbLen++)
		{
			int index=(pbLen+position)%capacity;
			if(table[index]!=null&&table[index].key==key)
				return true;
		}
		return false;
	}
	public void display()
	{
		for(int i=0;i<capacity;i++)
		{
			if (this.table[i] != null) {
			    System.out.println("(" + this.table[i].key + "," + this.table[i].psl + ")");
			} else {
			    System.out.println("Table index " + i + " is empty.");
			}		}
	}
	
	public void rehash()
	{
		int nextCapacity=0;
		if(capacity==5)
		{
			nextCapacity=11;
		}
		else if(capacity==11)
		{
			nextCapacity=19;
		}
		RobinHood rehash=new RobinHood(nextCapacity);
		
		for(int i=0;i<capacity;i++)
		{
			if(table[i]!=null)
				rehash.insert(table[i].key);
		}
		this.capacity=rehash.capacity;
		this.maxProbeLength=rehash.maxProbeLength;
		this.size=rehash.size;
		this.table=rehash.table;
	}
	
	
	
	

	public static void main(String[] args)
	{
		
		RobinHood r=new RobinHood(5);
		r.insert(6);
		r.insert(16);
		r.insert(7);
		r.insert(13);
		r.insert(27);
		r.insert(0);
		r.insert(3);
		r.insert(4);
		r.insert(5);

		System.out.println(r.search(99));
		r.display();

	}

}
