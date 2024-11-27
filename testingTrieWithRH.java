import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class testingTrieWithRH {
    public static void testCasesAdvance(){
        TrieWithRobinhood trie = new TrieWithRobinhood();

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


    static public void testCasesSimple(){
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
        
           
    }

    public static void testCaseProthem(){
        TrieWithRobinhood trie = new TrieWithRobinhood();
        trie.insert("plan");
        trie.insert("plan");

        trie.insert("plant");
        trie.insert("plant");
        
        trie.insert("plane");
        trie.insert("plane");
        trie.insert("plane");
        trie.insert("plane");
        trie.insert("plane");

        trie.insert("plans");
        trie.insert("planet");
        trie.insert("planet");

        trie.insert("planning");
        trie.insert("planning");
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
        trie.heap.displayHeapAsTree();
        //System.out.println("\nWords starting with 'pla':");
        //trie.prothema("pla");
//
        //System.out.println("\nWords starting with 'play':");
        //trie.prothema("play");
//
        //System.out.println("\nWords starting with 'xyz':");
        //trie.prothema("xyz");
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
        trie.display();
        trie.heap.displayHeapAsTree();
    }
    
    public static void testCaseProthemWithSizeTolerance(){
        // Create a new instance of the trie
        TrieWithRobinhood trie = new TrieWithRobinhood();

        // Insert words into the trie
        trie.insert("cat");
        trie.insert("ca");
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

        trie.display();
        // Test words with prothemaWithSizeTolerance
        String[] testWords = { "cat", "bat", "rat", "bath", "castle", "rattles", "bathtub" };

        for (String word : testWords) {
            System.out.println("\nWords similar to '" + word + "' within size tolerance:");
            trie.prothemaWithSizeTolerance(word);
        }
    }
      

    public static void currentTesting(){
         //testCaseProthemWithSizeTolerance();
         TrieWithRobinhood trie = new TrieWithRobinhood();
         trie.insert("aplan");
            //trie.insert("berry");
         trie.insert("plan");
         trie.insert("pan");
         trie.insert("lan");
         trie.insert("planet");
         trie.insert("planner");


         trie.insert("plains");
         trie.display();
         trie.prothemaWithSizeTolerance("plan");
         System.out.println("prothemata testing: plan");
    }
    public static void basic(){
        TrieWithRobinhood tr = new TrieWithRobinhood();
        tr.insert("antonios");
        tr.insert("ant");
        tr.insert("antreas");
        tr.insert("kalattas");
        tr.insert("antonios");
        tr.insert("john");
        tr.insert("john");

        tr.display();
        System.out.println(tr.search("ant"));
    }
    public static void main(String[] args) {
        TrieWithRobinhood trie = new TrieWithRobinhood();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            trie.insert(scan.next());;
        }
        trie.display();
        trie.pushToHeap();
        trie.heap.displayHeapAsTree();
    }
}
