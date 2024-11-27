import java.util.Random;
public class RandomStringGenerator {
		public static String WordGenerator(int length)
		{
			String characters="abcdefghijklmnopqrstuvwxyz";
			Random rand=new Random();
			char [] ch=new char[length];
			for(int i=0;i<length;i++)
			{
				ch[i]=characters.charAt(rand.nextInt(characters.length()));
			}
			String randomString ="";
			for(int i=0;i<ch.length;i++)
			{
				randomString+=ch[i];
			}
			return randomString;
			
		}
		
		public static void main(String[]args)
		{
			int length=5;
			System.out.println(WordGenerator(length));
		}
}
