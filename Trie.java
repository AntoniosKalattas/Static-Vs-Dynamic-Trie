public class Trie {
    TrieNode node;

    public Trie(){
        this.node = new TrieNode();
    }

    public class TrieNode{

        public final int numberOfLetters = 26;
        private int wordLength=0;
        private TrieNode childrenNodes[];

        public TrieNode(){
            childrenNodes = new TrieNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++)
                childrenNodes[i]=null;
        }

        public void insert(String word, int i){
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                if(this.childrenNodes[letter]==null)
                    this.childrenNodes[letter] = new TrieNode();            
                this.childrenNodes[letter].insert(word,++i);
            }
            else{
                this.wordLength = i;
            }
        }

        public boolean search(String word, int i){
            if(i==word.length() && this.wordLength!=0)
                return true;
            else if(i==word.length() && this.wordLength==0){
                return false;
            }
            else if(this.childrenNodes[word.charAt(i)-'a']!=null){
                return  this.childrenNodes[word.charAt(i)-'a'].search(word, ++i);
            }
            else{
                return false;
            }
        }

        public void display(){
            for(int i=0;i<numberOfLetters;i++){
                if(childrenNodes[i]!=null){
                    if(this.wordLength!=0)
                        System.out.print(" @ ");
                    System.out.print((char)(i+'a')+" ");
                    childrenNodes[i].display();
                }
            }
        }


    }


    public void insert(String word){
        node.insert((word).toLowerCase(), 0);
    }

    public boolean search(String lookingFor){
        return node.search(lookingFor.toLowerCase(), 0);
    }

    public void display(){
        node.display();
    }

    public static void main(String[] args) {
        Trie tr = new Trie();
        tr.insert("antonios");
        tr.insert("gg");
        tr.insert("antonioskalattas");
        tr.display();
        System.out.println();
        System.out.println(tr.search("An"));
    }
}