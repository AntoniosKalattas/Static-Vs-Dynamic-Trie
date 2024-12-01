    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;



    public class TrieWithRobinhood {
        private static final double LOAD_FACTOR_THRESHOLD = 0.9;
        static int numberOfNodes = 1;    // counters for calculating the total memory.
        static int totalSize=0;     // >> >> >> >> >> >>> >> >> >> >> >> 

        //public static int MemmoryCalculatinFunction(int N)
            
        TrieNode root = new TrieNode(-97,5);
        Heap heap = new Heap(20); /////////////////////////////////////////////////////////
        
        public class Element{   // 8 bytes
            int data;                   //the charecter that represents.
            int offset;

            public Element(int data){
                this.data = data;
            }
            
        }

        public class TrieNode{          // 48 bytes
            Element element;
            TrieNode array[];           // all of its sub-tries.
            int wordLength=0;           // when word comes to an end.
            int size;                   // size of the array.
            int currentlyInside=0;      // counter to keep track, how many elemnts currently insed the array.
            int maxCollitions;          // max number of collitions. To know how many 'hops' until you may found the letter.
            int importance =1;          // how many times did a word got inser.
            
            public TrieNode(){
                element = new Element(0);
                this.element.data=0;
                this.size=10;
                this.array=new TrieNode[this.size];
                this.maxCollitions=0;
            }

            public TrieNode(int size){
                element = new Element(0);
                this.element.data=0;
                this.size=size;
                this.array = new TrieNode[size];
                this.maxCollitions=0;
            }

            public TrieNode(int data, int size){
                element = new Element(data);
                this.size=size;
                this.array=new TrieNode[size];
                this.maxCollitions=0;
            }
            // this method will return a deep copy of its, given argument.
            public TrieNode deepCopy(TrieNode source) {
                if(source==null) return null;
            
                TrieNode tr = new TrieNode(source.element.data, source.size);
                tr.element.offset = source.element.offset;
                tr.wordLength = source.wordLength;
                tr.importance = source.importance;
                tr.maxCollitions = source.maxCollitions;
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

                TrieNode temp = new TrieNode(word.charAt(i)-'a',this.size);    // create a temp variable that will store the new word current character.
                temp.element.offset = 0;

                int index = (word.charAt(i)-'a')%size;                    // find where should we store the data.

                // DO NOT DELETE. //////////////////////////////////////
                //System.out.println("letter: "+word.charAt(i) + " index is: " + index);
                //if(array[index]!=null){
                //    System.out.println("Collition should occure because there is an lement existing: " + (char)(array[index].data+'a'));
                //}

                boolean exist = false;                                      // flag used when we have found that the same letter exist so we skip the insertion of that character.
                boolean swap = false;                                        // we use swap flag so when we will swap we will store the index of the first swap in the savedIndex. So in the nest insertion we will move from there.
                int savedIndex=0;
                int savedOffset=0;
                /// loop counters
                int loopIndex = index;
                int j=0;

                while(j<=maxCollitions+1){
                    // search trie in case the character exist.
                    if(array[loopIndex]!=null && array[loopIndex].element.data==word.charAt(i)-'a'){
                        index = loopIndex;
                        exist=true;
                    }
                    j++;
                    loopIndex=(loopIndex+1)%size;
                }
                if(!exist){                                                 // if the character does not exist.
                    this.currentlyInside++;                                 // increase the counter that countrs how many letters are inside.
                    if((double) currentlyInside/size>LOAD_FACTOR_THRESHOLD){    // if by inserting this letter we will pass the LFT that means we need to refactor and then reHash().
                        reHash();
                    }  

                    index = (word.charAt(i)-'a') % size;                        // re-calculate the index that we need to position the new element.
                    while(array[index]!=null){                                    // loop until you find a null position. because then we will just assing the new element there.
                        if(array[index].element.offset<temp.element.offset){                    // if the current inside elemnt has a bigger offset than the one that we are moving aroung -> swap them and start moving that around the array until you find a null pointer.
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
                        
                        index=(index+1)%size;                             // increase the index++.
                        temp.element.offset++;                                    // increase the offset of the object we are curently moving around.
                        if(temp.element.offset>maxCollitions)                    // check if the currently object offset is bigger than the maxCollition .
                            maxCollitions=temp.element.offset;
                    }
                    array[index] = deepCopy(temp);                        // because array[index] is a null position, that means we need to assing it to the  element we moved arround.
                } 
                if(swap==false)                                          // if we didn't swap the elements that means the index!=0 and savedIndex =0. so we need to make savedIndex = index.
                    savedIndex = index;
                array[savedIndex].insert(word,i+1, exist & existingWord);    // recursive call to the savedIndex. where the new insertion occure.
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
                //used for debug DO NOT DELETE

                TrieNode newArray[] = new TrieNode[newSize];
                //deep copy for all the element in the array.
                for(int i=0;i<size;i++){
                    if(this.array[i]!=null){
                        int letter = array[i].element.data;
                        int newPosition = letter % newSize;
                        if(newArray[newPosition]==null){
                            newArray[newPosition] = new TrieNode();
                            newArray[newPosition] = deepCopy(array[i]);
                        }
                        else{
                            while(newArray[newPosition]!=null){                                    // loop until you find a null position. because then we will just assing the new element there.
                                if(newArray[newPosition].element.offset<array[i].element.offset){  // if the current inside elemnt has a bigger offset than the one that we are moving aroung -> swap them and start moving that around the array until you find a null pointer.                                    
                                    TrieNode tn = deepCopy(newArray[newPosition]);
                                    newArray[newPosition] = deepCopy(array[i]);
                                    array[i]= deepCopy(tn);
                                }
                                newPosition=(newPosition+1)%newSize;
                                array[i].element.offset++;
                            }   
                            if(array[i].element.offset>=maxCollitions)
                                maxCollitions = array[i].element.offset;
                            newArray[newPosition] = new TrieNode();
                            newArray[newPosition] = deepCopy(array[i]);

                        }
                    }
                }
                this.size = newSize;
                this.array=newArray;
            }
            // This will search the try using the optimal robinhood search method, it will return the end of that word. 
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
                while(x<= maxCollitions){                        // loop for all possible collitions.
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


            public void display(String prefix, String childrenPrefix) {
                if (this.element.data != -97) { // Root node check
                    System.out.print(prefix + (char) (this.element.data + 'a'));
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

            //CeckPoint is using search. So checkPoint is in its optimas position using optimal search methods of robinhood search.
            public void prothema(TrieNode CheckPoint, String proth){   
                if(CheckPoint==null)
                    return;
                if(CheckPoint.wordLength!=0){             
                    //System.out.println(proth + " with importance: "+CheckPoint.importance);
                    Heap.Thing thing = heap.new Thing(proth, CheckPoint.importance);
                    heap.insert(thing);
                    //heap.displayHeap();
                }
                for(int i=0;i<CheckPoint.size;i++)
                    if(CheckPoint.array[i]!=null){
                        char c=(char)(CheckPoint.array[i].element.data+'a');
                        prothema(CheckPoint.array[i],proth+c);
                    }
            }

            public void prothemaWithTolerance(String word, int i, String proth, int misses){
                if(i==word.length()){
                    if(wordLength!=0){
                        //System.out.println("Found word: " +proth + " importance is: " + importance);
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
        
            public void prothemaWithSizeTolerance(String word, int i, String proth){
                //System.out.println("Smaller words: ");
                prothemaWithSmallerSize(word, 0, 0, "",0,true);
                //System.out.println("Bigger words: ");
                prothemataWithBiggerSize(word, 0, "", 0,true);
            }
            public void prothemataWithBiggerSize(String word, int i, String proth, int miss, boolean change){
                if(miss>2){
                    return;
                }
                if(wordLength>word.length() && wordLength<=word.length()+2 && change){
                    //System.out.println(proth);
                    Heap.Thing thing = heap.new Thing(proth, importance);
                    heap.insert(thing);
                }
                if(i>word.length()+2){
                    return;
                }
                if(i<word.length()) 
                    for(int j=0;j<size;j++){
                        if(array[j]!=null){
                            char c = (char)(array[j].element.data+'a');
                            if(array[j].element.data!=word.charAt(i)-'a')
                                array[j].prothemataWithBiggerSize(word, i, proth+c, miss+1, false);
                            if(array[j].element.data==word.charAt(i)-'a')
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
                if(wordLength!=0){
                    //System.out.println(proth);
                    Heap.Thing thing = heap.new Thing(proth, importance);
                    heap.insert(thing);
                }
                if(i==word.length())
                    return;
                

                for(int j=prevIndex;j<size;j++){
                    if(array[j]!=null){
                            char c =(char)(word.charAt(i)-'a');
                            if(array[j].element.data!=c)
                                prothemaWithSmallerSize(word, i+1, miss+1, proth,j ,false);
                            if(array[j].element.data==c)
                                array[j].prothemaWithSmallerSize(word, i+1, miss,(proth+(char)(c+'a')),0,true);
                    }
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
                while (x <= maxCollitions) {
                    if (array[index] != null && array[index].element.data == charIndex) {
                        return array[index].getImportance(word, i + 1);
                    }
                    index = (index + 1) % size;
                    x++;
                }
                return -1; // Word not found
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

        public int getImportance(String word) {
            return root.getImportance(filter(word), 0);
        }

        public void insert(String word){
            if(word==null)
                return;
            String filterdWord = filter(word.toLowerCase());
            //System.out.println(filterdWord);
            root.insert(filterdWord, 0,true);   
            //display();
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
        // Filter will filter out the unwanted characters. filter(String) will remove the non ascii characters ,where filter(String, Int) will remove the characters that are not letters.
        public String filter(String word){
            String newWord = "";
      
            for(int i = 0; i < word.length(); ++i) {
               if ((word.charAt(i)=='.' || word.charAt(i)=='-') && i!= word.length()-1)
                  return null;
               if (word.charAt(i)>='a' && word.charAt(i)<='z')
                  newWord = newWord+word.charAt(i);
            }
      
            return newWord;
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
        
        public void calculateMemory(){
            root.calculateMemory(); 
            double totalMemory = totalSize;
            System.out.printf( "Number of Nodes: %d(+1)\t \t \tTotal size: %.2f \n",(numberOfNodes)-1,totalMemory);
            System.out.printf("Converted to MB: %f", bytesToMegabytes((double)(totalMemory)));
        }

        public void pushToHeap(){
            root.pushToHeap("");
        }

        public void displayHeap(){
            heap.displayHeapAsTree();
        }

        public double bytesToMegabytes(double bytes) {
            return bytes / (1024.0 * 1024.0);
        }
        public static void main(String[] args){  
            TrieWithRobinhood tr = new TrieWithRobinhood();
            //** Calculate the memory for the given .txt fiel */
            Scanner scan  = new Scanner(System.in);
            while(scan.hasNext()){
                tr.insert(scan.next());
            }
            tr.calculateMemory();
            scan.close();
            /*********************************************************************************** */

            //tr.insert("A");
            //tr.insert("B");
            //tr.insert("C");
            //tr.insert("D");
            //tr.insert("E");
            //tr.insert("F");
            //tr.insert("G");
            //tr.insert("X");
            //tr.insert("O");
            //tr.insert("AntoniosKalattas");
            //tr.calculateMemory();
            //System.out.println();
        }
    }
