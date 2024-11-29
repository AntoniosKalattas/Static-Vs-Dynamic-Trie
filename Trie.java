
import java.util.Scanner;

public class Trie {
    TrieNode node;
    static int cnt=0;
    
    public Trie(){
        this.node = new TrieNode();
    }
    	public class TrieNode{
        // trienode array 4 bytes refenrecen *26 length.
        private TrieNode childrenNodes[];
        //int 7x4 ints.
        public final int numberOfLetters = 26;
        private int wordLength=0;
        private int importance=1;
        //// Gia eksisoropoish mnimis me RB version.
        int curentlyInside=0;
        int size=0;
        int a=0;
        int b=0;

        public TrieNode(){
            childrenNodes = new TrieNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++)
                childrenNodes[i]=null;
        }

        public void insert(String word, int i){
        	if(word==null)
        		return;
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                char currentChar = word.charAt(i);
                if (currentChar < 'a' || currentChar > 'z') {
                    throw new IllegalArgumentException("Invalid character in word: " + currentChar);
                }
                if(this.childrenNodes[letter]==null)
                {
                    this.childrenNodes[letter] = new TrieNode();
                    cnt++;
                }
                this.childrenNodes[letter].insert(word, i + 1);
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
        node.insert(filter((word).toLowerCase()), 0);
    }

    public boolean search(String lookingFor){
        return node.search(lookingFor.toLowerCase(), 0);
    }

    public void display(){
        node.display("");
    }
    
    
    public String filter(String word) {
        String newWord = "";
  
        for(int i = 0; i < word.length(); ++i) {
           if ((word.charAt(i)=='.' || word.charAt(i)=='-') && i != word.length() - 1)
              return null;  
           if (word.charAt(i)>='a' && word.charAt(i)<='z') {
              newWord = newWord + word.charAt(i);
           }
           
        }
  
        return newWord;
     }
     
    public static void main(String[] args){
        var runtime = Runtime.getRuntime();

        Trie tr = new Trie();
        //tr.insert("okay");
        System.out.println("Total memory consumed "+cnt*416);
    }
}