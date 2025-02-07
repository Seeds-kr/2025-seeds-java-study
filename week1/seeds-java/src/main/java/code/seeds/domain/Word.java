package code.seeds.domain;

public class Word {
    public static final int WORD_LENGTH = 5;
    private final String value;

    private Word(String value){
        this.value = value;
    }

    public static Word of(String input){
        if (input == null){
            throw new IllegalArgumentException("입력값은 null일 수 없습니다.");
        }

        String trimmed = input.trim();

        if (trimmed.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("단어는 5글자 알파벳 이어야 합니다.");
        }

        if (!containsOnlyAlphabets(trimmed)) {
            throw new IllegalArgumentException("단어는 5글자 알파벳 이어야 합니다.");
        }

        return new Word(trimmed);
    }

    private static boolean containsOnlyAlphabets(String input) {
        for (char c : input.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                System.out.println(c);
                return false;
            }
        }
        return true;
    }

    public Hint compareWith(Word guess){
        int length = WORD_LENGTH;

        boolean[] matchedTarget = new boolean[length];
        boolean[] matchedGuess  = new boolean[length];


        int matchCount = calculateExactMatches(this.value, guess.value, matchedTarget, matchedGuess);
        int misplacedCount = calculateMisplacedMatches(this.value, guess.value, matchedTarget, matchedGuess);

        return new Hint(matchCount, misplacedCount);
    }

    private int calculateExactMatches(String target, String guess, boolean[] matchedTarget, boolean[] matchedGuess) {
        int matchCount = 0;
        int length = target.length();

        for (int i = 0; i < length; i++) {
            if (target.charAt(i) == guess.charAt(i)) {
                matchCount++;
                matchedTarget[i] = true;
                matchedGuess[i] = true;
            }
        }
        return matchCount;
    }

    private int calculateMisplacedMatches(String target, String guess, boolean[] matchedTarget, boolean[] matchedGuess){
        int misplacedCount = 0;
        int length = target.length();

        for (int i = 0; i < length; i++){
            if(!matchedGuess[i]){
                char ch = guess.charAt(i);
                for (int j = 0; j < length; j++){
                    if (!matchedTarget[j] && target.charAt(j) == ch){
                        misplacedCount++;
                        matchedGuess[j] = true;
                        break;
                    }
                }
            }
        }
        return misplacedCount;
    }

    public String getValue(){
        return value;
    }
}
