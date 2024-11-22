import java.util.ArrayList;
import java.util.List;

public class TrieWithRobinhood {
    TrieNode root = new TrieNode(-97,5);
    
    public class TrieNode{

        private class prothemata{
            String word="";
            int importance=0;
        }

        private static final double LOAD_FACTOR_THRESHOLD = 0.9;
        int wordLength=0;
        int data=0;
        int offset=0;
        int size;
        TrieNode array[];
        int currentlyInside=0;
        int maxColitions;
        int importance =1;
        
        
        
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



        // handles the insert word.
        public void insert(String word, int i, boolean existingWord){
            if(word==null)                                              // if word is null because of the filter return.
                return;
            if(i==word.length()){                                       // if recursivle we reached the end of the word set the wordLength to the word.leangth() and return.
                this.wordLength=word.length();
                if(existingWord)
                    this.importance++;
                return;
            }

            TrieNode temp = new TrieNode(word.charAt(i),this.size);    // create a temp variable that will store the new word current character.
            temp.data=word.charAt(i)-'a';
            temp.offset = 0;

            int index = (word.charAt(i)-'a') % size;                    // find where should we store the data.

            // DO NOT DELETE. //////////////////////////////////////
            //System.out.println("letter: "+word.charAt(i) + " index is: " + index);
            //if(array[index]!=null){
            //    System.out.println("Collition should occure because there is an lement existing: " + (char)(array[index].data+'a'));
            //}

            boolean exist = false;                                      // flag used when we have found that the same letter exist so we skip the insertion of that character.
            int saved_index=0;                                          // incase we swap the character because of the robinhood unfair swap, we will save the index.
            int saved_offset=0;                                         // for the same reason we will save the offset.

            for(int j=0;j<size;j++){
                if(array[j]!=null && array[j].data==word.charAt(i)-'a'){
                    index = j;
                    exist=true;
                }
            }
            if(!exist){
                this.currentlyInside++;

                if((double) currentlyInside/size>LOAD_FACTOR_THRESHOLD){
                    reHash();
                }  
                index = (word.charAt(i)-'a') % size;
                while(array[index]!=null){
                    if(array[index].data==(word.charAt(i)-'a')){
                        exist=true;
                        break;
                    }
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
                    if(temp.offset>maxColitions){
                        maxColitions=temp.offset;
                    }
                }
                if(saved_index==0){
                    //System.out.println("offset hasn't change so change it" + temp.offset);
                    saved_index=index;
                    saved_offset = temp.offset;
                    if(saved_offset>maxColitions)
                        maxColitions=saved_offset;
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
            } 
            //System.out.println("-------------------------------------------MaxCol: "+ maxColitions);

            array[index].insert(word,++i, exist & existingWord);
        }
        // if the items inside the array reach the load factor, it will rehash the table into an array that has 3 more extra spaces.
        public void reHash(){
            // increase the hash size by 3.
            int newSize = this.size +3;
            //used for debug DO NOT DELETE
            System.out.println("---------------------------------------------------------------------------------------------------------------reHashing  : " + newSize);

            TrieNode newArray[] = new TrieNode[newSize];
            //deep copy for all the element in the array.
            for(int i=0;i<size;i++){
                if(this.array[i]!=null){
                    int letter = array[i].data;
                    int newPosition = letter % newSize;
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

        public TrieNode search(String word, int i){
            if(word==null || word=="")                      // check if the word is null or it could be empty because of the filter().
                return null;
            if(i==word.length())                            // if with the recursion we passed the last character then return if in this position the wordLength is not 0.
                return this;
            int position = (word.charAt(i)-'a') % size;     // get the index where we should start looking for.
            boolean flag = false;                       


            // USED FOR DEBBUGING DO NOT DELETE

            //System.out.println("maxColitions: "+ maxColitions + "index is ; "+ position + " for charater: "+ ( word.charAt(i) - 'a') + " and size is: "+ this.size);
            //if(array[position]!=null)
            //System.out.println((char)(array[position].data + 'a'));
            //for(int g=0;g<size;g++){
            //    if(array[g]==null)
            //        System.err.print(" null ");
            //    else
            //        System.out.print(" "+(char)(array[g].data + 'a'));
            //}
            int j=position;                                 // counters used for the loop.
            int x=0;                                        // counters used fot the loop.
            while(x<= maxColitions){                        // loop for all possible collitions.
                if(array[j]!= null && array[j].data==word.charAt(i)-'a'){
                    flag=true;
                    return array[j].search(word,i+1); // Use i + 1 to avoid side effects of ++i
                }
                j=(j+1)%size; // Move to the next index, wrapping around if necessary
                x++; // Increment the counter
            }
            if(flag==true)
                return this;
            else
                return null;
        }

        

        public void display(String prefix, String childrenPrefix) {
            if (this.data != -97) { // Root node check
                System.out.print(prefix + (char) (this.data + 'a'));
                if (this.wordLength != 0) {
                    System.out.print(" (importance: " + this.importance + ")");
                }
                System.out.println();
            } else {
                System.out.println(prefix + "Root");
            }

            List<TrieNode> nonNullChildren = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (array[i] != null) {
                    nonNullChildren.add(array[i]);
                }
            }

            int childCount = nonNullChildren.size();
            for (int i = 0; i < childCount; i++) {
                TrieNode child = nonNullChildren.get(i);
                boolean isLast = (i == childCount - 1);

                String newPrefix = childrenPrefix + (isLast ? "`-- " : "|-- ");
                String newChildrenPrefix = childrenPrefix + (isLast ? "    " : "|   ");
                child.display(newPrefix, newChildrenPrefix);
            }
        }

        public void prothema(TrieNode CheckPoint, String proth){   
            if(CheckPoint==null)
                return;
            if(CheckPoint.wordLength!=0)             
                System.out.println(proth);
            for(int i=0;i<CheckPoint.size;i++)
                if(CheckPoint.array[i]!=null){
                    char c=(char)(CheckPoint.array[i].data+'a');
                    prothema(CheckPoint.array[i],proth+c);
                }
        }

        public void prothemaWithTolerance(String word, int i, String proth, int misses){
            if(i==word.length()){
                if(wordLength!=0){
                    System.out.println("Found word: " +proth + " importance is: " + importance);
                }
                return;
            }
            if(word==null || misses>2) 
                return;
            for(int j=0;j<size;j++){
                if(array[j]!=null){
                    char c=(char)(array[j].data+'a');
                    if(array[j].data!=(word.charAt(i)-'a'))
                        array[j].prothemaWithTolerance(word,i+1,proth+c,misses+1);

                    else
                        array[j].prothemaWithTolerance(word,i+1,proth+c,misses);
                }
            }
        }
      
        public void prothemaWithSizeTolerance(String word, int i, String proth){
            if(i<word.length()+2){
                return;
            }
            if(wordLength!=0 && i+1==word.length()){
                System.out.println(proth);
            }
            if(i>=word.length() && wordLength!=0){
                System.out.println(proth);   
            }
            for(int j=0;j<size;j++)
                if(array[j]!=null){
                    char c=(char)(array[j].data+'a');
                    if(array[j].data!=word.charAt(i))
                        prothemaWithSizeTolerance(word, i+1, proth+c);
                    if(array[j].data==word.charAt(i))
                        prothemaWithSizeTolerance(word, i+1, proth+c);
                }
        }

        public int getImportance(String word, int i) {
            if (word == null || word.isEmpty())
                return -1;
            if (i == word.length())
                return this.importance;
            int charIndex = word.charAt(i) - 'a';
            int index = charIndex % size;
            int x = 0;
            while (x <= maxColitions) {
                if (array[index] != null && array[index].data == charIndex) {
                    return array[index].getImportance(word, i + 1);
                }
                index = (index + 1) % size;
                x++;
            }
            return -1; // Word not found
        }
    
    }

    public int getImportance(String word) {
        return root.getImportance(filter(word), 0);
    }

    public void insert(String word){
        if(word==null)
            return;
        String filterdWord = filter(word);
        root.insert(filterdWord, 0,true);       
    }

    public boolean search(String lookingFor){
        TrieNode search =  root.search(filter(lookingFor),0);
        if(search==null)
            return false;
        
        return search.wordLength!=0;
    }

    public void display(){
        root.display("" , "");
    }
    
    public String filter(String word){
        if(word==null)
            return "";
        String filterdWord = word.replaceAll("[^\\x00-\\x7F]", "");
        if(word.equals(filterdWord)==false)
            return null;
        filterdWord = filter((word).toLowerCase(), 0);
        return filterdWord;
    }
    
    public String filter(String word, int i){
        if(i==word.length())
            return "";
        if(word.charAt(i)>='a' && word.charAt(i)<='z')
            return word.charAt(i) + filter(word, ++i);
        else
            return "" + filter(word, ++i);
    }
    

    //Prothema wuthout tolerance.
    public void prothema(String word){
        TrieNode wordCheckPoint=root.search(word, 0);
        if(wordCheckPoint!=null)
            root.prothema(wordCheckPoint, word);
        else 
            System.out.println("No words found with prefix: " + word);
        
    }

    public void prothemaWithTolerance(String word){
            root.prothemaWithTolerance(word, 0, "",0 );
            //System.out.println("No words found with prefix: " + word);
    }
    
    public void prothemaWithSizeTolerance(String word){
        root.prothemaWithSizeTolerance(word, 0,"");
    }
    static public void testCases(){
        TrieWithRobinhood trie = new TrieWithRobinhood();
        
            // Test 1: Basic Insertions and Searches
            System.out.println("Test 1: Basic Insertions and Searches");
            String[] words1 = {"apple", "bat", "cat"};
            for (String word : words1) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after basic insertions:");
            trie.display();
        
            System.out.println("\nSearching for inserted words:");
            for (String word : words1) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\nSearching for non-inserted word 'dog':");
            System.out.println("Search 'dog': " + trie.search("dog"));
        
            System.out.println("\n----------------------------------\n");
        
            // Test 2: Insertion of Words Causing Collisions
            System.out.println("Test 2: Insertion of Words Causing Collisions");
            String[] words2 = {"ad", "ae", "af", "ag", "ah"};
            for (String word : words2) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after collision insertions:");
            trie.display();
        
            System.out.println("\nSearching for collided words:");
            for (String word : words2) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 3: Insertion Leading to Rehashing
            System.out.println("Test 3: Insertion Leading to Rehashing");
            String[] words3 = {"ai", "aj", "ak", "al", "am", "an", "ao", "ap", "aq", "ar"};
            for (String word : words3) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after rehashing insertions:");
            trie.display();
        
            System.out.println("\nSearching for rehashed words:");
            for (String word : words3) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 4: Insertions with Shared Prefixes
            System.out.println("Test 4: Insertions with Shared Prefixes");
            String[] words4 = {"ant", "anteater", "antelope", "antonym"};
            for (String word : words4) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after shared prefix insertions:");
            trie.display();
        
            System.out.println("\nSearching for shared prefix words:");
            for (String word : words4) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\nSearching for prefix 'an':");
            System.out.println("Search 'an': " + trie.search("an"));
        
            System.out.println("\n----------------------------------\n");
        
            // Test 5: Edge Cases - Empty String and Single Character
            System.out.println("Test 5: Edge Cases - Empty String and Single Character");
            trie.insert("");
            trie.insert("a");
            System.out.println("Trie Structure after edge case insertions:");
            trie.display();
        
            System.out.println("\nSearching for empty string and 'a':");
            System.out.println("Search '': " + trie.search(""));
            System.out.println("Search 'a': " + trie.search("a"));
        
            System.out.println("\n----------------------------------\n");
        
            // Test 6: Insertions with Uppercase Letters
            System.out.println("Test 6: Insertions with Uppercase Letters");
            String[] words6 = {"Apple", "BaT", "CaT", "Dog", "dOg"};
            for (String word : words6) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after uppercase insertions:");
            trie.display();
        
            System.out.println("\nSearching for words with different cases:");
            for (String word : words6) {
                System.out.println("Search '" + word.toLowerCase() + "': " + trie.search(word.toLowerCase()));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 7: Non-Alphabetic Characters (should handle or ignore)
            System.out.println("Test 7: Non-Alphabetic Characters");
            String[] words7 = {"hello-world", "123", "test123", "foo_bar"};
            for (String word : words7) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after non-alphabetic insertions:");
            trie.display();
        
            System.out.println("\nSearching for words with non-alphabetic characters:");
            for (String word : words7) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 8: Large Number of Insertions to Test Performance and Rehashing
            System.out.println("Test 8: Large Number of Insertions");
            List<String> words8 = new ArrayList<>();
            for (char c1 = 'a'; c1 <= 'z'; c1++) {
                for (char c2 = 'a'; c2 <= 'z'; c2++) {
                    String word = "" + c1 + c2;
                    words8.add(word);
                    trie.insert(word);
                }
            }
            System.out.println("Trie Structure after large number of insertions:");
            trie.display();
        
            System.out.println("\nSearching for some words in large dataset:");
            String[] searchWords = {"aa", "mm", "zz", "ab", "zy", "nonexistent"};
            for (String word : searchWords) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 9: Insertions with Repeating Characters
            System.out.println("Test 9: Insertions with Repeating Characters");
            String[] words9 = {"aaaa", "aaab", "aaac", "aaad"};
            for (String word : words9) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after repeating character insertions:");
            trie.display();
        
            System.out.println("\nSearching for words with repeating characters:");
            for (String word : words9) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 10: Insertions and Searches with Long Words
            System.out.println("Test 10: Insertions and Searches with Long Words");
            String[] words10 = {"supercalifragilisticexpialidocious", "pneumonoultramicroscopicsilicovolcanoconiosis"};
            for (String word : words10) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after long word insertions:");
            trie.display();
        
            System.out.println("\nSearching for long words:");
            for (String word : words10) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 11: Insertions with All Identical Characters
            System.out.println("Test 11: Insertions with All Identical Characters");
            String[] words11 = {"aaaaa", "aaaaaa", "aaaaaaa"};
            for (String word : words11) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after identical character insertions:");
            trie.display();
        
            System.out.println("\nSearching for words with all identical characters:");
            for (String word : words11) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 12: Searching for Prefixes
            System.out.println("Test 12: Searching for Prefixes");
            String[] words12 = {"ant", "anteater", "antelope"};
            for (String word : words12) {
                // Words already inserted in previous tests
                System.out.println("Search prefix '" + word.substring(0, 2) + "': " + trie.search(word.substring(0, 2)));
            }
        
            System.out.println("\n----------------------------------\n");
        
            // Test 13: Edge Cases - Null and Non-Lowercase Input
            System.out.println("Test 13: Edge Cases - Null and Non-Lowercase Input");
            trie.insert(null);
            trie.insert("ÄÖÜ");
            System.out.println("Trie Structure after edge case insertions:");
            trie.display();
        
            System.out.println("\nSearching for null and non-lowercase input:");
            System.out.println("Search null: " + trie.search(null));
            System.out.println("Search 'ÄÖÜ': " + trie.search("ÄÖÜ"));
        
            System.out.println("\n----------------------------------\n");
        
            // Test 14: Insertions with Non-English Characters
            System.out.println("Test 14: Insertions with Non-English Characters");
            String[] words14 = {"café", "naïve", "résumé", "touché"};
            for (String word : words14) {
                System.out.println("Inserting: " + word);
                trie.insert(word);
            }
            System.out.println("Trie Structure after non-English character insertions:");
            trie.display();
        
            System.out.println("\nSearching for words with non-English characters:");
            for (String word : words14) {
                System.out.println("Search '" + word + "': " + trie.search(word));
            }
    }

    public static void testCaseProthem(){
        TrieWithRobinhood trie = new TrieWithRobinhood();
        trie.insert("plan");
        trie.insert("plant");
        trie.insert("plane");
        trie.insert("plans");
        trie.insert("planet");
        trie.insert("planning");
        trie.insert("plank");
        trie.insert("play");
        trie.insert("player");
        trie.insert("played");
        trie.insert("playing");

        // Display the trie structure
        System.out.println("Trie Structure:");
        trie.display();

        // Test the prothema method with various prefixes
        System.out.println("\nWords starting with 'plan':");
        trie.prothema("plan");

        System.out.println("\nWords starting with 'pla':");
        trie.prothema("pla");

        System.out.println("\nWords starting with 'play':");
        trie.prothema("play");

        System.out.println("\nWords starting with 'xyz':");
        trie.prothema("xyz");
    }
    public void testCases2Importance(){
        TrieWithRobinhood trie = new TrieWithRobinhood();

        // Insert words into the trie.
        trie.insert("hello");
        trie.insert("world");
        trie.insert("hello");
        trie.insert("help");
        trie.insert("world");
        trie.insert("help");
        trie.insert("hello");
        trie.insert("test");
        trie.insert("test");
        trie.insert("testing");

        // Display the trie structure along with importance values.
        trie.display();

        // Test the importance variable.
        String[] words = { "hello", "world", "help", "test", "testing", "unknown" };

        System.out.println("\nWord Importance Values:");
        for (String word : words) {
            int importance = trie.getImportance(word);
            if (importance != -1) {
                System.out.println("Word: " + word + ", Importance: " + importance);
            } else {
                System.out.println("Word: " + word + " not found in trie.");
            }
        }
    }
    public static void prothemWIthToleranceTestCases(){
        TrieWithRobinhood trie = new TrieWithRobinhood();
        trie.insert("play");
        trie.insert("clan");
        trie.insert("plum");
        trie.insert("span");
        trie.prothemaWithTolerance("plan");
    }
    
    public static void testCaseProthemWithSizeTolerance(){
        // Create a new instance of the trie
        TrieWithRobinhood trie = new TrieWithRobinhood();

        // Insert words into the trie
        trie.insert("cat");
        trie.insert("cats");
        trie.insert("cast");
        trie.insert("castle");
        trie.insert("cattle");
        trie.insert("bat");
        trie.insert("batch");
        trie.insert("bath");
        trie.insert("baths");
        trie.insert("bathroom");
        trie.insert("rat");
        trie.insert("rate");
        trie.insert("rates");
        trie.insert("rating");
        trie.insert("rattle");

        // Test words with prothemaWithSizeTolerance
        String[] testWords = { "cat", "bat", "rat", "bath", "castle", "rattles", "bathtub" };

        for (String word : testWords) {
            System.out.println("\nWords similar to '" + word + "' within size tolerance:");
            trie.prothemaWithSizeTolerance(word);
        }
    }
        
    
    public static void main(String[] args){
        testCaseProthemWithSizeTolerance();
    }
}