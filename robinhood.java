public class robinhood {
    int size = 10;
    int maxColitions=0;
    RobinHoodeNode array[] = new RobinHoodeNode[size];
    int currentlyInside=0;
    public class RobinHoodeNode{
        int data=0;
        int offset=0;
        public RobinHoodeNode(int data){
            this.data = data;
            this.offset=0;
        }
    }

    public void insert(int data){
        this.currentlyInside++;
        if(this.currentlyInside/this.size>=0.9)
            reHash(size*2);
        int index=data%size;
        RobinHoodeNode newElement = new RobinHoodeNode(data);
        while(array[index]!=null){
            if(index>=size-1)
                index=0;

            if(array[index].offset<newElement.offset){
                RobinHoodeNode temp = new RobinHoodeNode(array[index].data);
                temp.offset = array[index].offset;
                array[index].data=newElement.data;
                array[index].offset = newElement.offset;
                newElement.data = temp.data;
                newElement.offset = temp.offset;
                
            }
            newElement.offset++;
            if(maxColitions<newElement.offset){
                this.maxColitions=newElement.offset;
                System.out.println("max col is : " + maxColitions);
            }
            index++;
        }
        this.array[index]=newElement;
        
    }

    public void remove(int elementToRemove){
        int startingIndex = elementToRemove%size;
        int secondIndex = -1;
        System.out.println("starting index is: "+ startingIndex);
        for(int i=startingIndex;i-startingIndex<maxColitions;i++){
            if(array[i].data==elementToRemove){
                array[i].data=0;
                secondIndex =i;
                break;
            }
        }
        if(secondIndex!=-1){
            int i=0;
            for(i=secondIndex; array[i]!=null && (array[i+1]!=null && array[i+1].offset!=0);i++){
                array[i].data=array[i+1].data;
                array[i].offset=array[i+1].offset-1;
                array[i+1].data=0;
                array[i+1].offset=0;
            }
            array[i]=null;
        }
        else
            System.out.println("Element not found");
    }

    public void reHash(int newSize){
        System.out.println("rehashing");
        System.out.println("new size is: "+newSize);
        RobinHoodeNode newArray[] = new RobinHoodeNode[newSize];
        for(int i=0;i<size;i++){
            newArray[i]=array[i];
        }
        this.size=newSize;
        this.array=new RobinHoodeNode[newSize];
        for(int i=0;i<size;i++){
            if(newArray[i]!=null)
                this.insert(newArray[i].data);
        }
        return;
    }

    public void display(){
        System.out.println("displaying ----------------------");
        for(int i=0;i<size;i++){
            if(array[i]!=null)
            System.out.println(this.array[i].data + " :: " + array[i].offset);
            else
                System.out.println("null");
        }
    }
    
    public static void main(String[] args) {
        robinhood rb = new robinhood();
        rb.insert(10);
        rb.insert(4);
        rb.insert(5);
        rb.insert(2);
        rb.display();
        rb.insert(1222);
        rb.display();
        rb.insert(142);
        rb.insert(1);
        //rb.insert(101);
        //rb.insert(33);
        //rb.insert(99);

        rb.display();

        rb.remove(2);
        rb.display();
    }
}
