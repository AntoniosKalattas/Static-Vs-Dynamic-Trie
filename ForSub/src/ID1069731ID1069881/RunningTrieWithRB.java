package ID1069731ID1069881;

import java.util.Scanner;
import java.io.*;

public class RunningTrieWithRB{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TrieWithRobinhood tr = new TrieWithRobinhood();
        System.out.println("Please give file");

        //read from file
        try {
            String fileName = scan.next();
            Scanner fileScanner = new Scanner(new File(fileName));
            
            while (fileScanner.hasNext()) {
                String word = fileScanner.next();
                tr.insert(word);
            }
            
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(10);
        }
        ////////////////////////
        System.out.println("1  -> Find Similar");
        System.out.println("2  -> Most important words");

        int choice  = scan.nextInt();
        if(choice==1){
            while(true){
                System.out.println("please give k and then word");
                tr.pushRecommendedWordToHeap(scan.nextInt(), scan.next());
            }
        }
        if(choice==2){
                System.out.println("please give k:");
                tr.pushToHeap(scan.nextInt());
        }
        scan.close();
    }
}
