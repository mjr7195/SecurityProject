import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Security {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter String:");
        String userString = scanner.nextLine();
        String spacePerson = convertToSpacePerson(userString);
        System.out.println("Space Person String: " +spacePerson);

        String hashResult = calculateHashValue(userString);
        System.out.println("Hash Value: " + hashResult);

        int shiftValue = 5;
        String encryptCipher = caesarCipher(userString, shiftValue);
        System.out.println(userString + " shifted by " + shiftValue + " is " + encryptCipher);

        bruteForce(encryptCipher);
    }

    public static String convertToSpacePerson(String text) {
        //(~ ! @ # $ % ^ & *)
        HashMap<Character, String> spacePersonMap = new HashMap<>();
        spacePersonMap.put('A', "~"); spacePersonMap.put('B', "!@"); spacePersonMap.put('C', "^*@");
        spacePersonMap.put('D', "!"); spacePersonMap.put('E', "#%"); spacePersonMap.put('F', "#@*");
        spacePersonMap.put('G', "@"); spacePersonMap.put('H', "$%"); spacePersonMap.put('I', "%&^");
        spacePersonMap.put('J', "#"); spacePersonMap.put('K', "!@#"); spacePersonMap.put('L', "*&@");
        spacePersonMap.put('M', "%"); spacePersonMap.put('N', "$#%"); spacePersonMap.put('O', "^%#");
        spacePersonMap.put('P', "^"); spacePersonMap.put('Q', "^&*"); spacePersonMap.put('R', "@#$");
        spacePersonMap.put('S', "&"); spacePersonMap.put('T', "*&^"); spacePersonMap.put('U', "*&%");
        spacePersonMap.put('V', "*"); spacePersonMap.put('W', "%$@"); spacePersonMap.put('X', "$^@");
        spacePersonMap.put('Y', "~!"); spacePersonMap.put('Z', "@^&"); spacePersonMap.put('1', "%#*");
        spacePersonMap.put('2', "@#"); spacePersonMap.put('3', "*&@"); spacePersonMap.put('4', "&@^");
        spacePersonMap.put('5', "^&"); spacePersonMap.put('6', "#^%"); spacePersonMap.put('7', "*%#");
        spacePersonMap.put('8', "*~"); spacePersonMap.put('9', "&$!"); spacePersonMap.put('0', "^$@");

        StringBuilder spacePersonBuilder = new StringBuilder();
        text = text.toUpperCase();

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                spacePersonBuilder.append(" ");
            } else if (spacePersonMap.containsKey(c)) {
                spacePersonBuilder.append(spacePersonMap.get(c)).append(" ");
            }
        }

        return spacePersonBuilder.toString().trim();
    }

    public static String calculateHashValue(String input) {
        try {
            // Create a MessageDigest object for SHA-256
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");

            // Update the digest with the bytes of the input string
            byte[] hashedBytes = sha256Digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String caesarCipher(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) (base + newAlphabetPosition);
                encryptedText.append(newCharacter);
            } else {
                encryptedText.append(character);
            }
        }

        return encryptedText.toString();
    }

    public static void bruteForce(String encryptedText) {
        for (int shift = 0; shift < 26; shift++) {
            StringBuilder decryptedText = new StringBuilder();

            for (char character : encryptedText.toCharArray()) {
                if (character != ' ' && character != '!' && character != '.' && character != ',') { // Check if character is not a space or punctuation
                    int originalPosition = character - 'A';
                    int newPosition = (originalPosition - shift) % 26;
                    newPosition = newPosition < 0 ? newPosition + 26 : newPosition;
                    char newCharacter = (char) ('A' + newPosition);
                    decryptedText.append(newCharacter);
                } else {
                    decryptedText.append(character);
                }
            }

            System.out.println(shift + ": " + decryptedText);
        }
    }



}
