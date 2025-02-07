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
                return false;
            }
        }
        return true;
    }

    /**
     * 두 단어 비교 시 필요한 정보들을 하나의 객체로 캡슐화합니다.
     */
    private static class MatchContext {
        String target;
        String guess;
        boolean[] matchedTarget;
        boolean[] matchedGuess;

        public MatchContext(String target, String guess, boolean[] matchedTarget, boolean[] matchedGuess) {
            this.target = target;
            this.guess = guess;
            this.matchedTarget = matchedTarget;
            this.matchedGuess = matchedGuess;
        }
    }

    public Hint compareWith(Word guess){
        int length = WORD_LENGTH;

        boolean[] matchedTarget = new boolean[length];
        boolean[] matchedGuess  = new boolean[length];


        int matchCount = calculateExactMatches(this.value, guess.value, matchedTarget, matchedGuess);
        MatchContext matchContext = new MatchContext(this.value, guess.value, matchedTarget, matchedGuess);
        int misplacedCount = calculateMisplacedMatches(matchContext);

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

    private int calculateMisplacedMatches(MatchContext context) {
        int misplacedCount = 0;
        int length = context.target.length();
        for (int i = 0; i < length; i++) {
            if (!context.matchedGuess[i]) {  // 이미 정확한 매칭이 처리되지 않은 인덱스에 대해
                char ch = context.guess.charAt(i);
                if (tryMatchAndMark(ch, context)) {
                    misplacedCount++;
                }
            }
        }
        return misplacedCount;
    }

    /**
     * guess 문자열의 문자 중, 아직 매칭되지 않은(정확히 맞지 않은) 위치에 대해,
     * target 문자열에서 해당 문자가 미매칭 위치에 존재하면 매칭 처리하고 true를 반환합니다.
     *
     * @param guessChar 검사할 문자
     * @param context   매칭 컨텍스트 (target, guess, matchedTarget, matchedGuess 포함)
     * @return 매칭에 성공하면 true, 그렇지 않으면 false
     */
    private boolean tryMatchAndMark(char guessChar, MatchContext context) {
        int length = context.target.length();
        for (int j = 0; j < length; j++) {
            if (!context.matchedTarget[j] && context.target.charAt(j) == guessChar) {
                context.matchedTarget[j] = true;
                return true;
            }
        }
        return false;
    }

    public String getValue(){
        return value;
    }
}
