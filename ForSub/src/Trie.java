import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trie {
    TrieNode node;
    static int cnt=0;
    
    public Trie(){
        this.node = new TrieNode();
    }
    	public class TrieNode{
        public final int numberOfLetters = 26;
        private int wordLength=0;
        private TrieNode childrenNodes[];
        private int importance=1;
        

        public TrieNode(){
            childrenNodes = new TrieNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++)
                childrenNodes[i]=null;
        }

        public void insert(String word, int i){
        	
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                if(this.childrenNodes[letter]==null)
                {
                    this.childrenNodes[letter] = new TrieNode();
                    cnt++;
                }
                this.childrenNodes[letter].insert(word,++i);
            }
            else{
                if(wordLength!=0)
                    importance++;
                this.wordLength = i;
            }
        }

        public boolean search(String word, int i){
            if(i==word.length() && this.wordLength!=0)
                return true;
            else if(i==word.length() && this.wordLength==0)
                return false;
            else if(this.childrenNodes[word.charAt(i)-'a']!=null)
                return  this.childrenNodes[word.charAt(i)-'a'].search(word, ++i);
            else
                return false;
        }

        public void display(String prefix){
            if(this.wordLength!=0)
                System.out.println(prefix+" (importance: "+importance+")");
            for(int i=0;i<numberOfLetters;i++)
                if(childrenNodes[i]!=null)
                    childrenNodes[i].display(prefix+(char)(i+'a'));
        }
        
    }


    public void insert(String word){
        node.insert((word).toLowerCase(), 0);
    }

    public boolean search(String lookingFor){
        return node.search(lookingFor.toLowerCase(), 0);
    }

    public void display(){
        node.display("");
    }
    public static void insertWordsFromFile(String filePath, Trie trie) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing spaces
                if (!word.isEmpty()) { // Avoid inserting empty strings
                    trie.insert(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Trie tr = new Trie();
        insertWordsFromFile("words.txt",tr);
        tr.display();
        System.out.println();
        System.out.println(tr.search("An"));
        int TotalMemoryConsumed=cnt*232;
        System.out.println("Total Bytes:"+TotalMemoryConsumed);
    }
}