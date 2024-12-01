import java.util.Random;
public class RandomStringGenerator {
		//takes the length and returns a randomly generated String
		public static String WordGenerator(int length)
		{
			String characters="abcdefghijklmnopqrstuvwxyz";
			Random rand=new Random();
			char [] ch=new char[length];
			for(int i=0;i<length;i++)
			{
				//gemerates a random string from 0-characters.length
				ch[i]=characters.charAt(rand.nextInt(characters.length()));//gemerates a random string from 0-characters.length
			}
			String randomString ="";
			//concatenation logic from the array of characters to a string
			for(int i=0;i<ch.length;i++)
			{
				randomString+=ch[i];
			}
			return randomString;
			
		}
		
		public static void main(String[]args){
			ShiftedPoissonGenerator generator = new ShiftedPoissonGenerator(6.94); // Mean word length

			// For random length words.
			for(int i=0;i<1000000;i++){
				System.out.println(WordGenerator(generator.nextShiftedPoisson()));
			}
			// For length 8 words
			for(int i=0;i<1000000;i++){
				System.out.println(WordGenerator(8));
			}
		}
}
