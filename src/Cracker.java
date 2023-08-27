import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Cracker {

	public Cracker() {
	}
	static String[] readFile(String file) throws IOException {
		
	        FileReader fileReader = new FileReader(file);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        List<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line);
	        }
	        bufferedReader.close();
	        return lines.toArray(new String[lines.size()]);
	    }
	

	public static void main(String[] args) throws NoSuchAlgorithmException {

		String[] shadowPassword;
		String[] users = new String[10];
		String[] salts = new String[10];
		String[] hashes = new String[10];

		try {
			shadowPassword = readFile("shadow");
			
			for (int i=0; i< shadowPassword.length; i++) {
				users[i] = shadowPassword[i].substring(0, 5);
				salts[i] = shadowPassword[i].substring(9, 17);
				hashes[i] = shadowPassword[i].substring(18, 40);
			}

			String[] dictionary = readFile("common-passwords.txt");
			for (int j = 0; j < hashes.length; j++) {
				for (int i=0; i < dictionary.length; i++) {
					
					String hashChecker = MD5Shadow.crypt(dictionary[i], salts[j]);
					
					if (hashChecker.equals(hashes[j])) {
						System.out.println(users[j] + ":" + dictionary[i]);
						System.out.println("-----------------------");
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		System.exit(0);
		}
}