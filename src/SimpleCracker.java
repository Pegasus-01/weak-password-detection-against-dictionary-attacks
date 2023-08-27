import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class SimpleCracker {

    public SimpleCracker() {
    }
    static String[] readFile(String file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }


    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }


    public static void main(String[] args) {

        MessageDigest hashGenerator;

        try {
            hashGenerator = MessageDigest.getInstance("MD5");

            String[] shadowPassword = readFile("testShadow.txt");

            for (String str: shadowPassword) {
                String[] parts = str.split(":");
                String users = parts[0];
                String salts = parts[1];
                String hashes = parts[2];

                String[] dictionary = readFile("common-passwords.txt");
                for(String password: dictionary){
                    byte saltedHash[] = (salts + password).getBytes();
                    String hashCheck = toHex(hashGenerator.digest(saltedHash));
                    if(hashCheck.equals(hashes)){
                        System.out.println(users + ": " + password);
                        System.out.println("-----------------------");
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        System.exit(0);
    }
}