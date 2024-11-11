

public class Trie{
    private static final int numberOfLetters = 26;
    TrieNode[] TrieNodes;
    public class TrieNode{
            int wordLength=0;
            TrieNode children[];
        public TrieNode(){
            children = new TrieNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++)
                children[i]=null;
        }
        public void insert(String word, int i){
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                if(this.children[letter]==null)
                    this.children[letter] = new TrieNode();            
                this.children[letter].insert(word,++i);
            }
            else
                this.wordLength = i;
            
        }
        public void display(){
                
            for(int i=0;i<numberOfLetters;i++){
                if(children[i]!=null){
                    System.out.print(" "+(char)(i + 'a'));
                    if(children[i].wordLength!=0)
                        System.out.print("  @  ");
                    children[i].display();
                }
            }
        }
        public boolean isPresent(String s,int cnt) {

    		if(s.length()==cnt)
    			return true;
    		if(this.children[s.charAt(cnt)-'a']!=null)
    			return this.children[s.charAt(cnt)-'a'].isPresent(s,cnt+1);
    		else
    			return false;
    	}
    }
    	
    
    public Trie(){
        this.TrieNodes=new TrieNode[numberOfLetters];
        for(int i=0;i<numberOfLetters;i++)
            TrieNodes[i]=null;
    }

    public void insert(String word){
        int firstLetter = word.charAt(0) - 'a';
        if(TrieNodes[firstLetter]==null){            
            TrieNodes[firstLetter] = new TrieNode();
        }
        TrieNodes[firstLetter].insert(word,1);
    }
    
    public boolean isPresent(String word) {
    	if(TrieNodes[word.charAt(0)-'a']!=null)
    		return TrieNodes[word.charAt(0)-'a'].isPresent(word, 1);
    	return false;
    }

    public void display(){
        for(int i=0;i<numberOfLetters;i++){
            if(TrieNodes[i]!=null){
                System.out.print((char)(i + 'a'));
                TrieNodes[i].display();
                System.out.println();
            }
        }
    }

    public static void main(String args[]){
        Trie tr = new Trie();
        tr.insert("antonios");
        //tr.display();
        tr.insert("ant");
        tr.insert("gigga");
        tr.display();
        String s="a";
        System.out.println(tr.isPresent(s));
    }
}
