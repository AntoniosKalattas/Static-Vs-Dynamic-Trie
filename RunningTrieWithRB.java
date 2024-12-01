import java.util.Scanner;

public class RunningTrieWithRB{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TrieWithRobinhood tr = new TrieWithRobinhood();

        while(scan.hasNext())
            tr.insert(scan.next());
        System.out.println("1  -> for display");
        System.out.println("2  -> Find Similar");

        int choice  = scan.nextInt();
        if(choice==1){
            tr.display();
        }
        if(choice==2){
            System.out.println("please give k:");
            int k = scan.nextInt();
            tr.pushToHeap(k);
        }
    }
}