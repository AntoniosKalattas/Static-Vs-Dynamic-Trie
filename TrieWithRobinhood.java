import java.util.ArrayList;
import java.util.List;

public class TrieWithRobinhood {
    TrieNode root = new TrieNode(-97,5);
    
    public class TrieNode{
        private static final double LOAD_FACTOR_THRESHOLD = 0.9;
        int wordLength=0;
        int data=0;
        int offset=0;
        int size;
        TrieNode array[];
        int currentlyInside=0;
        int maxColitions;

        
        
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
            if(word==null)
                return;
            if(i==word.length()){
                this.wordLength=word.length();
                return;
            }

            TrieNode temp = new TrieNode(word.charAt(i),this.size);
            temp.data=word.charAt(i)-'a';
            temp.offset = 0;
            int index = (word.charAt(i)-'a') % size;
            //System.out.println("letter: "+word.charAt(i) + " index is: " + index);
            //if(array[index]!=null){
            //    System.out.println("Collition should occure because there is an lement existing: " + (char)(array[index].data+'a'));
            //}
            boolean exist = false;
            int saved_index=0;
            int saved_offset=0;

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
            array[index].insert(word,++i);
        }
           
        public void reHash(){
            int newSize = this.size +3;
            System.out.println("---------------------------------------------------------------------------------------------------------------reHashing  : " + newSize);

            TrieNode newArray[] = new TrieNode[newSize];

            for(int i=0;i<size;i++){
                if(this.array[i]!=null){
                    int letter = array[i].data;
                    //System.out.print("letter that I am about to rehash is: " + (char)(letter+'a'));
                    int newPosition = letter % newSize;
                    //System.out.println(" its new possition is: "+ newPosition);
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

        public boolean search(String word, int i){
            if(word==null || word=="")
                return false;
            if(i==word.length())
                return this.wordLength!=0;
            int position = (word.charAt(i)-'a') % size;   
            boolean flag = false;
            //System.out.println("maxColitions: "+ maxColitions + "index is ; "+ position + " for charater: "+ ( word.charAt(i) - 'a') + " and size is: "+ this.size);
            //if(array[position]!=null)
            //System.out.println((char)(array[position].data + 'a'));
            //for(int g=0;g<size;g++){
            //    if(array[g]==null)
            //        System.err.print(" null ");
            //    else
            //        System.out.print(" "+(char)(array[g].data + 'a'));
            //}
            int j=position;
            int x=0;
            while( x <= maxColitions) {
                if(array[j] != null && array[j].data == word.charAt(i) - 'a') {
                    flag = true;
                    return array[j].search(word, i + 1); // Use i + 1 to avoid side effects of ++i
                }
                j = (j + 1) % size; // Move to the next index, wrapping around if necessary
                x++; // Increment the counter
            }
            return flag;
        }

        

        public void display(String prefix, String childrenPrefix) {
            if (this.data != -97) { // Root node check
                System.out.println(prefix + (char)(this.data + 'a'));
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

    }

    public void insert(String word){
        if(word==null)
            return;
        String filterdWord = filter(word);
        root.insert(filterdWord, 0);       
    }

    public boolean search(String lookingFor){
        return root.search(filter(lookingFor),0);
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
        
        public static void main(String[] args) {
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
        
     
}