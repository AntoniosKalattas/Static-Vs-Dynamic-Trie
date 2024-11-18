

public class Trie {

	public TriNode Root;
	private int wordlength=0;
	public Trie()
	{
		Root=new TriNode();
	}
	public void insert(String s)
	{
		Root.insert(s,0);
	}
	
	public void printTrie() {
	    Root.printTrieHelper("");
	}
	public boolean search(String s)
	{
		return this.Root.search(s,0);
	}
	public class TriNode
	{
		public TriNode[]children;
		static final int AlphabetSize=52;
		private int wordlength;
		
	public TriNode()
	{
		children=new TriNode[AlphabetSize];
		for(int i=0;i<AlphabetSize;i++)
		{
			children[i]=null;
		}
	}
	public void insert(String s,int cnt)
	{
		
		if(cnt==s.length())
		{
			this.wordlength=s.length();
			return;
		}
		
		char currentChar=s.charAt(cnt);
        int index;
        if(currentChar>='a'&&currentChar<='z') {
            index=currentChar-'a'+26;
        } else {
            index=currentChar-'A';
        }
        
        if(this.children[index]==null) {
            this.children[index]=new TriNode();
        }
        this.children[index].insert(s,cnt+1);
    
	}
	private void printTrieHelper(String prefix) {
	    if(this.wordlength > 0) {
	        System.out.println(prefix);  // Print the word when reaching a leaf node
	    }

	    for(int i = 0; i < 52; i++) {
	        if(this.children[i] != null) {
	            char c;
	            if(i < 26) {
	                c = (char) ('A' + i);  // Uppercase letters
	            } else {
	                c = (char) ('a' + (i - 26));  // Lowercase letters
	            }
	            this.children[i].printTrieHelper(prefix + c);  // Append the character to the prefix
	        }
	    }
	}

	
	
		
	public boolean search(String s, int cnt) {
        if(cnt == s.length()) {
            return this.wordlength != 0;
        }

        char currentChar = s.charAt(cnt);
        int index;
        if(currentChar >= 'a' && currentChar <= 'z') {
            index = currentChar - 'a' + 26;
        } else {
            index = currentChar - 'A';
        }

        return this.children[index] != null && this.children[index].search(s, cnt+1);
    }
	}
	public static void main(String[]args)
	{
		Trie tree=new Trie();
		String s="mourtou";
		tree.insert("TEsTo");
        tree.insert("TEAM");
        tree.printTrie();
		System.out.println(tree.search("TEsTo"));
	}

}

