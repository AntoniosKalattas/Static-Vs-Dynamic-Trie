    public class Trie_v2 {
        TrieNode node;

        public Trie_v2(){
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

            public boolean exist(String word, int i){
                if(i==word.length() && this.wordLength!=0)
                    return true;
                else if(i==word.length() && this.wordLength==0){
                    return false;
                }
                else if(this.childrenNodes[word.charAt(i)-'a']!=null){
                    return  this.childrenNodes[word.charAt(i)-'a'].exist(word, ++i);
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
                        System.out.print((char)(i+'a')+"");
                        childrenNodes[i].display();
                    }
                }
            }


        }


        public void insert(String word){
            node.insert(word, 0);
        }

        public boolean exist(String lookingFor){
            return node.exist(lookingFor, 0);
        }

        public void display(){
            node.display();
        }

        public static void main(String[] args) {
            Trie_v2 tr = new Trie_v2();
            tr.insert("antonios");
            tr.insert("gg");
            tr.insert("antonioskalattas");
            tr.display();
            System.out.println();
            System.out.println(tr.exist("gg"));
        }
    }
