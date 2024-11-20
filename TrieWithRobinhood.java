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
            this.maxColitions=0;
        }

        public TrieNode(int size){
            this.data=0;
            this.size=size;
            this.array = new TrieNode[size];
            this.maxColitions=0;
        }

        public TrieNode(int data, int size){
            this.data = data;
            this.size=size;
            this.array=new TrieNode[size];
            this.maxColitions=0;
        }

        public void insert(String word, int i){
            
            if(i==word.length()){
                this.wordLength=word.length();
                return;
            }
            this.currentlyInside++;

            if ((double) currentlyInside / size > LOAD_FACTOR_THRESHOLD)
                reHash();

            TrieNode temp = new TrieNode(word.charAt(i),this.size);
            temp.data=word.charAt(i)-'a';
            temp.offset = 0;
            int index = (word.charAt(i)-'a') % size;
            //System.out.println("letter: "+word.charAt(i) + " index is: " + index);
            //if(array[index]!=null){
            //    System.out.println("Collition should occure because there is an lement existing: " + (char)(array[index].data+'a'));
            //}
            int saved_index=0;
            int saved_offset=0;
            while(array[index]!=null){
                if(array[index].data==(word.charAt(i)-'a'))
                    break;
                if(array[index].offset<temp.offset){
                    TrieNode ntemp = new TrieNode();
                   // System.out.println("letter to switch  is : " + (char )(array[index].data+'a')+ " while inserting: " + word.charAt(i));
                    ntemp.data = array[index].data;
                    ntemp.offset = array[index].offset;
                   // System.out.println("I have the letter : "+(char)(ntemp.data+'a'));
                    
                    if(saved_index==0){
                        //System.out.println("collition occured I will save the offset: "+ temp.offset);
                        saved_index=index;
                        saved_offset=temp.offset;
                    }
                    array[index].data=temp.data;
                    array[index].offset = temp.offset;

                    temp.data = ntemp.data;
                    temp.offset = ntemp.offset;
                    //System.out.println("now I hold the letter: " + (char)(temp.data+'a'));
                }

                index= (index + 1 )% size;
                temp.offset++;
            }
            if(saved_index==0){
                //System.out.println("offset hasn't change so change it" + temp.offset);
                saved_index=index;
                saved_offset = temp.offset;
            }
            if(saved_index!=0){
                //System.out.println("collition has occured");
                if(array[index]==null){

                    array[index]=new TrieNode();
                    array[index].data=temp.data;
                    array[index].offset = temp.offset;   
                }
                else
                    array[index].data=temp.data;
                    array[index].offset = temp.offset;   
            }
            if(array[index]==null){

                array[index]=new TrieNode();
                array[index].data=word.charAt(i)-'a';
                array[index].offset = saved_offset;   
            }
            array[index].insert(word,++i);
        }
           
        public void insert(TrieNode element){
            
        }
        
        public void reHash(){
            System.out.println("---------------------------------------------------------------------------------------------------------------reHashing");
            int newSize = this.size +3;
            TrieNode newArray[] = new TrieNode[newSize];

            for(int i=0;i<size;i++){
                if(this.array[i]!=null){
                    int letter = array[i].data;
              //      System.out.print("letter that I am about to rehash is: " + (char)(letter+'a'));
                    int newPosition = letter % newSize;
                //    System.out.println(" its new possition is: "+ newPosition);
                    newArray[newPosition] = new TrieNode();
                    newArray[newPosition].data = array[i].data;
                    newArray[newPosition].offset = array[i].offset;
                    newArray[newPosition].array = array[i].array;
                    newArray[newPosition].currentlyInside = array[i].currentlyInside;
                    newArray[newPosition].maxColitions = array[i].maxColitions;
                    newArray[newPosition].wordLength = array[i].wordLength;
                    newArray[newPosition].size = array[i].size;
                }
            }
            this.size = newSize;
            this.array=newArray;
        }

        void display(){
            System.out.print((char)(this.data+'a'));
            if(wordLength!=0)
                System.out.print(" @ ");
            for(int i=0;i<size;i++){
                if(array[i]!=null){
                    array[i].display();
                }
                //else{
                //    System.err.print(" [null]");
                //}
            }
        }
    }

    public void insert(String word){
        root.insert((word).toLowerCase(), 0);       
        //for(int i=0;i<root.size;i++){
        //    if(root.array[i]!=null){
        //        System.out.print((char)(root.array[i].data+'a'));
        //    }
        //    else
        //        System.err.print(" [null]");
        //} 
        //System.out.println();
    }

    public boolean search(String lookingFor){
       //return node.search(lookingFor.toLowerCase(), 0);
       return false;
    }

    public void display(){
        root.display();
    }

    public static void main(String[] args) {
        TrieWithRobinhood trie = new TrieWithRobinhood();
        
        // Test 1: Basic Insertions without Collisions
        System.out.println("Test 1: Basic Insertions without Collisions");
        String[] words1 = {"apple", "bat", "cat"};
        for (String word : words1) {
            System.out.println("Inserting: " + word);
            trie.insert(word);
        }
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
        // Test 2: Insertions that Cause Collisions
        System.out.println("Test 2: Insertions that Cause Collisions");
        String[] words2 = {"ad", "ae", "af"}; // These words may hash to the same index
        for (String word : words2) {
            System.out.println("Inserting: " + word);
            trie.insert(word);
        }
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
        
        // Test 3: Inserting Words Leading to Rehashing
        System.out.println("Test 3: Inserting Words Leading to Rehashing");
        String[] words3 = {"ag", "ah", "ai", "aj", "ak", "al", "am", "an", "ao", "ap"};
        for (String word : words3) {
            System.out.println("Inserting: " + word);
            trie.insert(word);
        }
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
    
        // Test 4: Insertions with Different Lengths
        System.out.println("Test 4: Insertions with Different Lengths");
        String[] words4 = {"ant", "anteater", "antelope", "batman", "battery", "catapult"};
        for (String word : words4) {
            System.out.println("Inserting: " + word);
            trie.insert(word);
        }
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
    
        // Test 5: Edge Cases - Empty String and Single Character
        System.out.println("Test 5: Edge Cases - Empty String and Single Character");
        trie.insert("");
        trie.insert("a");
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
    
        // Test 6: Insertions with Upper and Lower Case (Assuming Case Insensitivity)
        System.out.println("Test 6: Insertions with Upper and Lower Case");
        String[] words6 = {"Apple", "BaT", "CaT", "Dog", "dOg"};
        for (String word : words6) {
            System.out.println("Inserting: " + word);
            trie.insert(word);
        }
        trie.display();
        System.out.println("\n------------------------------\n");
        trie = new TrieWithRobinhood();
    
        // Test 7: Insertions with Non-alphabetic Characters (Should Handle or Ignore)
        //System.out.println("Test 7: Insertions with Non-alphabetic Characters");
        //String[] words7 = {"hello-world", "123", "test123", "foo_bar"};
        //for (String word : words7) {
        //    System.out.println("Inserting: " + word);
        //    trie.insert(word);
        //}
        //trie.display();
        //System.out.println("\n------------------------------\n");
    //
        //// Future Work: Implement and Test Search Method
        //// System.out.println("Testing Search Method:");
        // System.out.println("Search 'apple': " + trie.search("apple"));
        // System.out.println("Search 'bat': " + trie.search("bat"));
        // System.out.println("Search 'unknown': " + trie.search("unknown"));
    }
    
}