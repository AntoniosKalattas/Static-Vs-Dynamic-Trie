

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
        public void push(String word, int i){
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                if(this.children[letter]==null)
                    this.children[letter] = new TrieNode();            
                this.children[letter].push(word,++i);
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

    }

    public Trie(){
        this.TrieNodes=new TrieNode[numberOfLetters];
        for(int i=0;i<numberOfLetters;i++)
            TrieNodes[i]=null;
    }

    public void push(String word){
        int firstLetter = word.charAt(0) - 'a';
        if(TrieNodes[firstLetter]==null){            
            TrieNodes[firstLetter] = new TrieNode();
        }
        TrieNodes[firstLetter].push(word,1);
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
        tr.push("antonios");
        //tr.display();
        tr.push("ant");
        tr.display();
    }
}