package ID1069731ID1069881;

import java.util.Scanner;

public class TrieWithRobinhood {
    private static final double LOAD_FACTOR_THRESHOLD = 0.9;
    // ********* Memory Calculation Counters ***************** //
    static int numberOfNodes = 1;       // counters for calculating the total memory.
    static int totalSize=0;             // >> >> >> >> >> >>> >> >> >> >> >> 
    // ******************************************************* //
    Heap heap;
    TrieNode root;
    public TrieWithRobinhood(){
        root = new TrieNode(-97,5);
    }
    public class Element{ 
        int data;                   //the character that represents.
        int offset;
        public Element(int data){
            this.data = data;
        }   
    }
    public class TrieNode{    
        Element element;
        TrieNode array[];           // all of its sub-tries.
        int wordLength=0;           // when word comes to an end.
        int size;                   // size of the array.
        int currentlyInside=0;      // counter to keep track, how many elements currently insed the array.
        int maxCollisions;          // max number of collisions. To know how many 'hops' until you may found the letter.
        int importance =1;          // how many times did a word got insert.
        
        public TrieNode(){
            element = new Element(0);
            this.element.data=0;
            this.size=10;
            this.array=new TrieNode[this.size];
            this.maxCollisions=0;
        }
        public TrieNode(int size){
            element = new Element(0);
            this.element.data=0;
            this.size=size;
            this.array = new TrieNode[size];
            this.maxCollisions=0;
        }
        public TrieNode(int data, int size){
            element = new Element(data);
            this.size=size;
            this.array=new TrieNode[size];
            this.maxCollisions=0;
        }
        // this method will return a deep copy of its, given argument.
        public TrieNode deepCopy(TrieNode source) {
            if(source==null) return null;
        
            TrieNode tr = new TrieNode(source.element.data, source.size);
            tr.element.offset = source.element.offset;
            tr.wordLength = source.wordLength;
            tr.importance = source.importance;
            tr.maxCollisions = source.maxCollisions;
            tr.currentlyInside = source.currentlyInside;
        
            tr.array = new TrieNode[source.size];
        
            // Recursively deep copy each child node
            for(int i=0;i<source.size;i++){
                if(source.array[i]!=null)
                    tr.array[i]=deepCopy(source.array[i]); // Recursion for deep copy
                else
                    tr.array[i] = null;
            }
            return tr;
        }
        
        // handles the insert word.
        public void insert(String word, int i, boolean existingWord){
            if(word==null)                                              // if word is null because of the filter return.
                return;
            if(i==word.length()){                                       // if recursivle we reached the end of the word set the wordLength to the word.leangth() and return.
                if(this.wordLength==0)
                    this.wordLength=word.length();
                else
                    if(existingWord)
                        this.importance++;
                return;
            }
            TrieNode temp = new TrieNode(word.charAt(i)-'a',this.size); // create a temp variable that will store the new word current character.
            temp.element.offset = 0;
            int index = (word.charAt(i)-'a')%size;                      // find where should we store the data.
            boolean exist = false;                                      // flag used when we have found that the same letter exist so we skip the insertion of that character.
            boolean swap = false;                                       // we use swap flag so when we will swap we will store the index of the first swap in the savedIndex. So in the nest insertion we will move from there.
            int savedIndex=0;
            int savedOffset=0;
            /// loop counters
            int loopIndex = index;
            int j=0;
            // optimal search algorithm in case the letter already exist.
            while(j<=maxCollisions+1){
                if(array[loopIndex]!=null && array[loopIndex].element.data==word.charAt(i)-'a'){
                    index = loopIndex;
                    exist=true;
                }
                j++;
                loopIndex=(loopIndex+1)%size;
            }
            if(!exist){                                                     // if the character does not exist.
                this.currentlyInside++;                                     // increase the counter that countrs how many letters are inside.
                if((double) currentlyInside/size>LOAD_FACTOR_THRESHOLD){    // if by inserting this letter we will pass the LFT that means we need to refactor and then reHash().
                    reHash();
                }  
                index = (word.charAt(i)-'a') % size;                        // re-calculate the index that we need to position the new element.
                while(array[index]!=null){                                  // loop until you find a null position. because then we will just assing the new element there.
                    if(array[index].element.offset<temp.element.offset){    // if the current inside elemnt has a bigger offset than the one that we are moving aroung -> swap them and start moving that around the array until you find a null pointer.
                        if(!swap){                                          // if its the first time you are swaping, store that index, in the saveindex because in the nest recursive call you need to go from there.
                            swap=true;                                  
                            savedIndex=  index;
                            savedOffset= element.offset;
                        }
                        // swaping procedure.
                        TrieNode tempNode = deepCopy(array[index]);
                        array[index] = deepCopy(temp);
                        temp = deepCopy(tempNode);
                    }
                    
                    index=(index+1)%size;                                   // increase the index++.
                    temp.element.offset++;                                  // increase the offset of the object we are curently moving around.
                    if(temp.element.offset>maxCollisions)                   // check if the currently object offset is bigger than the maxCollition .
                        maxCollisions=temp.element.offset;
                }
                array[index] = deepCopy(temp);                              // because array[index] is a null position, that means we need to assing it to the  element we moved arround.
            } 
            if(swap==false)                                                 // if we didn't swap the elements that means the index!=0 and savedIndex =0. so we need to make savedIndex = index.
                savedIndex = index;
            array[savedIndex].insert(word,i+1, exist & existingWord);       // recursive call to the savedIndex. where the new insertion occure.
        }
        // if the items inside the array reach the load factor, it will rehash the table into an array that has 3 more extra spaces.
        public void reHash(){
            int newSize=0;
            if(size==5) 
                newSize =11;
            if(size==11)
                newSize = 19;
            if(size==19)
                newSize = 29; 
            TrieNode newArray[] = new TrieNode[newSize];
            // similar to insert().
            for(int i=0;i<size;i++){
                if(this.array[i]!=null){                                // simple case where the slot is empty.
                    int letter = array[i].element.data;
                    int newPosition = letter % newSize;
                    if(newArray[newPosition]==null){
                        newArray[newPosition] = new TrieNode();
                        newArray[newPosition] = deepCopy(array[i]);     // copy that element to the new array.
                        newArray[newPosition].element.offset = 0;       // reset the offset in that object since the offset is 0.
                    }
                    else{                                               // if the slot isn't empty, loop until an empty one.
                        array[i].element.offset = 0;                                            
                        while(newArray[newPosition]!=null){                                     // loop until you find a null position. because then we will just assign the new element there.
                            if(newArray[newPosition].element.offset<array[i].element.offset){   // if the current inside element has a bigger offset than the one that we are moving around -> swap them and start moving that around the array until you find a null pointer.                                    
                                TrieNode tn = deepCopy(newArray[newPosition]);                  // swap procedure.
                                newArray[newPosition] = deepCopy(array[i]);
                                array[i]= deepCopy(tn);
                            }
                            newPosition=(newPosition+1)%newSize;
                            array[i].element.offset++;                                          // increase the offset of the elemnt that you are moving around.
                            if(array[i].element.offset>=maxCollisions)                          // check if the new offset is bigger thatn max.           
                                maxCollisions = array[i].element.offset;
                        }   
                        if(array[i].element.offset>=maxCollisions)                  
                            maxCollisions = array[i].element.offset;
                        newArray[newPosition] = new TrieNode();                                 // since newPosition is now null , just place the old elemnt there.
                        newArray[newPosition] = deepCopy(array[i]);
                    }
                }
            }
            System.gc();                                                                        // call the gc because of multiple deep copies.
            this.size = newSize;
            this.array=newArray;
        }
        // This will search the try using the optimal robinHood search method, it will return the end of that word. 
        // For example if we are searching for the word: PLAN, if it exist, the search() will return the pointer to the letter l, if not it will return the null pointer.
        public TrieNode search(String word, int i){
            if(word==null || word=="")                      // check if the word is null or it could be empty because of the filter().
                return null;
            if(i==word.length())                            // if with the recursion we passed the last character then return if in this position the wordLength is not 0.
                return this;
            int position = (word.charAt(i)-'a') % size;     // get the index where we should start looking for.
            boolean flag = false;                       
            int j=position;                                 // counters used for the loop.
            int x=0;                                        // counters used fot the loop.
            while(x<= maxCollisions){                        // loop for all possible collitions.
                if(array[j]!= null && array[j].element.data==word.charAt(i)-'a'){
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
        // 1st method to find prothema. Uses the return object from search, in order to save time.
        public void simpleProthema(TrieNode CheckPoint, String proth){   
            if(CheckPoint==null)
                return;
            if(CheckPoint.wordLength!=0){              // push word into heap.
                Heap.Thing thing = heap.new Thing(proth, CheckPoint.importance);
                heap.insert(thing);
            }   
            for(int i=0;i<CheckPoint.size;i++)         // for every node from check point go down.
                if(CheckPoint.array[i]!=null){
                    char c=(char)(CheckPoint.array[i].element.data+'a');
                    simpleProthema(CheckPoint.array[i],proth+c);
                }
        }
        // 2nd method to find prothema.
        public void prothemaWithTolerance(String word, int i, String proth, int misses){
            if(misses>2)    // if the missed characters are more than 2, break;
                return;
            if(i==word.length()){
                if(proth.length()==word.length() && wordLength!=0){ // push into heap.
                    Heap.Thing thing = heap.new Thing(proth, importance);
                    heap.insert(thing);
                }
                return;
            }
            if(word==null || misses>2) 
                return;
            for(int j=0;j<size;j++){
                if(array[j]!=null){
                    char c=(char)(array[j].element.data+'a');
                    if(array[j].element.data!=(word.charAt(i)-'a'))
                        array[j].prothemaWithTolerance(word,i+1,proth+c,misses+1);
                    else
                        array[j].prothemaWithTolerance(word,i+1,proth+c,misses);
                }
            }
        }
        // 3rd method to find prothema.
        public void prothemaWithSizeTolerance(String word, int i, String proth){
            //System.out.println("Smaller words: ");
            prothemaWithSmallerSize(word, 0, 0, "",0,true);
            //System.out.println("Bigger words: ");
            prothemataWithBiggerSize(word, 0, "", 0,true);
        }
        public void prothemataWithBiggerSize(String word, int i, String proth, int miss, boolean change){
            if(miss>2){                                                 // if the wrong accepted characters are more than 2, break.
                return;
            }
            if(wordLength>word.length() && wordLength<=word.length()+2 && change){
                Heap.Thing thing = heap.new Thing(proth, importance);   // push to heap.
                heap.insert(thing);
            }
            if(i>word.length()+2){
                return;
            }
            if(i<word.length()) 
                for(int j=0;j<size;j++){
                    if(array[j]!=null){
                        char c = (char)(array[j].element.data+'a');
                        if(array[j].element.data!=word.charAt(i)-'a')   // if characters dont much, contrinue to the next node.
                            array[j].prothemataWithBiggerSize(word, i, proth+c, miss+1, false);
                        if(array[j].element.data==word.charAt(i)-'a')   // if characters much, keep going to the next node.
                                array[j].prothemataWithBiggerSize(word, i+1, proth+c, miss,true);
                    }
                }
            if(i>=word.length()){
                for(int j=0;j<size;j++){
                    if(array[j]!=null){
                        char c = (char)(array[j].element.data+'a');
                        array[j].prothemataWithBiggerSize(word, i+1, proth+c, miss, true);
                    }
                }
            }
        }
        public void prothemaWithSmallerSize(String word, int i, int miss, String proth, int prevIndex, boolean change){
            if(miss>1)
                return;
            if(wordLength!=0){                                                  // push to heap.
                Heap.Thing thing = heap.new Thing(proth, importance);
                heap.insert(thing);
            }
            if(i==word.length())
                return;
            for(int j=prevIndex;j<size;j++){
                if(array[j]!=null){
                        char c =(char)(word.charAt(i)-'a');
                        if(array[j].element.data!=c)                            // if the character are not the same procced within the same object.
                            prothemaWithSmallerSize(word, i+1, miss+1, proth,j ,false);
                        if(array[j].element.data==c)                            // if the character mach, proceed to the next node.
                            array[j].prothemaWithSmallerSize(word, i+1, miss,(proth+(char)(c+'a')),0,true);
                }
            }
        }
    
        public void pushToHeap(String word){
            if(wordLength!=0){
                Heap.Thing thing = heap.new Thing(word, importance);
                heap.insert(thing);
            }
            
            for(int i=0;i<size;i++)
                if(array[i]!=null)
                    array[i].pushToHeap(word + (char)(array[i].element.data+'a'));
        }
        public void calculateMemory(){
            totalSize+= (64 + (12+(size*4)));
            for(int i=0;i<size;i++){
                if(array[i]!=null){
                    numberOfNodes++;
                    array[i].calculateMemory();
                }
            }
        }
    }
    // calls the inner insert().
    public void insert(String word){
        if(word==null)
            return;
        String filterdWord = filter(word.toLowerCase());
        //System.out.println(filterdWord);
        root.insert(filterdWord, 0,true);   
        //display();
    }
    // Will search the entire trie and will retuturn true if exist. false if not.
    public boolean search(String lookingFor){
        TrieNode search =  root.search(filter(lookingFor),0);
        if(search==null)
            return false;
        
        return search.wordLength!=0;
    }
    // Filter will filter out the unwanted characters. filter(String) will remove the non ascii characters ,where filter(String, Int) will remove the characters that are not letters.
    public String filter(String word){
        String newWord = "";
        if(word.equals("") || word == null )        // if word is an empty line or a null string.
            return null;
        for(int i = 0; i < word.length(); ++i) {
           if ((word.charAt(i)=='.' || word.charAt(i)=='-') && i!= word.length()-1)
              return null;
           if (word.charAt(i)>='a' && word.charAt(i)<='z')
              newWord = newWord+word.charAt(i);
        }
  
        return newWord;
    }
    
    //Will find all the prothemata(Simple).
    public void prothema(String word){
        TrieNode wordCheckPoint=root.search(word, 0);
        if(wordCheckPoint!=null)
            root.simpleProthema(wordCheckPoint, word);
        
    }
    // Prothemata that have max 2 different characters.
    public void prothemaWithTolerance(String word){
            root.prothemaWithTolerance(word, 0, "",0 );
            //System.out.println("No words found with prefix: " + word);
    }
    // Will find the prithemata that have different size.
    public void prothemaWithSizeTolerance(String word){
        root.prothemaWithSizeTolerance(word, 0,"");
    }
    
    // Will push the most important words into words. Used when we want to fidn the most importance word, in a text.
    public void pushToHeap(int k){ 
        heap = new Heap(k);
        root.pushToHeap("");
        heap.displayHeapAsTree();            
    }
    // Will call all the methods who find prothemata and will push then into a heap with a given k size.
    public void pushRecommendedWordToHeap(int k, String word){
        this.heap = new Heap(k);
        prothema(word);
        prothemaWithTolerance(word);
        prothemaWithSizeTolerance(word);
        
        heap.displayHeapAsTree();
    }
    // The following methods are used when trying to calculate the total memory used.
    public void calculateMemory(){
        root.calculateMemory(); 
        double totalMemory = totalSize;
        System.out.printf( "Number of Nodes: %d(+1)\t \t \tTotal size: %.2f \n",(numberOfNodes)-1,totalMemory);
        System.out.printf("Converted to MB: %f", bytesToMegabytes((double)(totalMemory)));
    }
    public double bytesToMegabytes(double bytes) {
        return bytes / (1024.0 * 1024.0);
    }
   
    public static void memoryCalculationForGivenFile(){
        TrieWithRobinhood tr = new TrieWithRobinhood();
        //** Calculate the memory for the given .txt fiel */
        Scanner scan  = new Scanner(System.in);
        while(scan.hasNext()){
            tr.insert(scan.next());
        }
        tr.calculateMemory();
        scan.close();
        /*********************************************************************************** */
    }
    public static void main(String[] args){  
    }
}