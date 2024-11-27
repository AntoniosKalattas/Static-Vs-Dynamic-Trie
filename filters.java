public class filters {
    public filters(){

    }   
    public String filterNonAsciiChars(String word){
        return null;
    }
    public String filterDotsBetween(String word){
        String newWord ="";
        for(int i=0;i<word.length();i++){
            if(word.charAt(i)=='.' && i!=word.length()-1){
                return null;
            }
            if(word.charAt(i)!='.')
                newWord+=word.charAt(i);
        }
        return newWord;
    }

    public static void main(String[] args) {
        filters filter = new filters();
        String word=  "StrigA";
        System.out.println(filter.filterDotsBetween(word));
    }
}
