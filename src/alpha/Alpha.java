package alpha;

public class Alpha {
    
    static public boolean isValid(char character) {
        return Character.isUpperCase(character);
    }
    
    static public boolean isValid(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!isValid(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    static public int code(char character) {
        if (!isValid(character))
            throw new IllegalArgumentException("character must be an alphabetic uppercase character");
        return (int) character - (int) 'A';
    }

    static public char toChar(int code) {
        if (code < 0 || 26 <= code) 
            throw new IllegalArgumentException("invalid character code");
        return (char) ((int) 'A' + code);
    }
}
