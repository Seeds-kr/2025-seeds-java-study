package code.seeds.util;

public class InputValidator {

    private InputValidator(){}

    public static String validate(String input){
        if (input == null){
            throw new IllegalArgumentException("입력값이 존재해야 합니다.");
        }

        String trimmedInput = input.trim(); //양쪽 공백 제거

        if(trimmedInput.length() != 5){
            throw new IllegalArgumentException("단어는 5자여야 합니다.");
        }

        if (!containsOnlyAlphabets(input)){
            throw new IllegalArgumentException("단어는 알파벳만 포함되어야 합니다.");
        }
        return trimmedInput;
    }

    private static boolean containsOnlyAlphabets(String input) {
        for (char c : input.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                return false;
            }
        }
        return true;
    }
}
