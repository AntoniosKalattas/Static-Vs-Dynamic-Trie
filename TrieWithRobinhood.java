public class TrieWithRobinhood {
    TrieNode root = new TrieNode(-97,10);
    
    public class TrieNode{
        private static final double LOAD_FACTOR_THRESHOLD = 0.9;
        int wordLength=0;
        int data=0;
        int offset=0;
        int size=10;
        TrieNode array[];
        int currentlyInside=0;
        int maxColitions=0;

        
        
        public TrieNode(){
            this.data=0;
            this.size=10;
            this.array=new TrieNode[this.size];
        }

        public TrieNode(int size){
            this.data=0;
            this.size=size;
            this.array = new TrieNode[size];
        }

        public TrieNode(int data, int size){
            this.data = data;
            this.size=size;
            this.array=new TrieNode[size];
        }

        public void insert(String word, int i){
            
            if(i==word.length()){
                this.wordLength=word.length();
                return;
            }
            this.currentlyInside++;

            if(currentlyInside/size >LOAD_FACTOR_THRESHOLD)
                reHash();

            TrieNode temp = new TrieNode(word.charAt(i),this.size);
            temp.offset = 0;
            int index = (word.charAt(i)-'a') % size;
            System.out.println("letter: "+word.charAt(i) + " index is: " + index);
            if(array[index]!=null){
                System.out.println("Collition should occure because there is an lement existing: " + (char)(array[index].data+'a'));
            }
            int saved_index=0;
            int saved_offset=0;
            while(array[index]!=null){
                if(index>=size)
                    index=0;
                if(array[index].data==(word.charAt(i)-'a'))
                    break;
                if(array[index].offset<temp.offset){
                    TrieNode ntemp = new TrieNode();
                    ntemp.data = array[index].data;
                    ntemp.offset = array[index].offset;
                    if(saved_index==0){
                        System.out.println("collition occured I will save the offset: "+ temp.offset);
                        saved_index=index;
                        saved_offset=temp.offset;
                    }
                    array[index].data=temp.data;
                    array[index].offset = temp.offset;

                    temp.data = ntemp.data;
                    temp.offset = ntemp.offset;

                }

                index++;
                temp.offset++;
            }
            if(saved_index==0){
                System.out.println("offset hasn't change so change it" + temp.offset);
                saved_index=index;
                saved_offset = temp.offset;
            }
            if(array[index]==null){

                array[index]=new TrieNode();
                array[index].data=word.charAt(i)-'a';
                array[index].offset = saved_offset;   
            }
            array[index].insert(word,++i);
        }
           
        public void reHash(){
            
        }

        void display(){
            System.out.print((char)(this.data+'a'));
            if(wordLength!=0)
                System.out.print(" @ ");
            for(int i=0;i<size;i++){
                if(array[i]!=null){
                    array[i].display();
                }
            }
        }
    }

    public void insert(String word){
        root.insert((word).toLowerCase(), 0);       
        for(int i=0;i<root.size;i++){
            if(root.array[i]!=null){
                System.out.print((char)(root.array[i].data+'a'));
                System.out.print(root.array[i].offset);
            }
            else
                System.out.print(" [null]");
        } 
        System.out.println();
    }

    public boolean search(String lookingFor){
       //return node.search(lookingFor.toLowerCase(), 0);
       return false;
    }

    public void display(){
        root.display();
    }

    public static void main(String[] args) {
        TrieWithRobinhood tr = new TrieWithRobinhood();
        tr.insert("antonios");
        tr.insert("ant");
        tr.insert("kala");
        tr.insert("uoh");
        tr.insert("bazzas");
        System.out.println("----------------------------------");
        System.out.println("Final Display");
        tr.display();
        
    }
}