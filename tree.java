

public class tree{
    private static final int numberOfLetters = 26;

    public class treeNode{
            int wordLength = 0;
            treeNode children[];
        public treeNode(){
            children = new treeNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++){
                children[i]=null;
            }    
        }
        public void push(String word, int i){
            int letter = word.charAt(i) - 'a';
            this.children[letter].push(word,++i);
        }
    }

    treeNode[] treeNodes;
    public tree(){
        this.treeNodes=new treeNode[numberOfLetters];
        for(int i=0;i<numberOfLetters;i++)
            treeNodes[i]=new treeNode();
    }

    public void push(String word){
        int firstLetter = word.charAt(0) - 'a';
        treeNodes[firstLetter].push(word,1);
    }

    public static void main(String args[]){
        tree tr = new tree();
        tr.push("antonios");
    }
}