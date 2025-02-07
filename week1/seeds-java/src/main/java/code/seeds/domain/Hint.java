package code.seeds.domain;

public class Hint {
    private final int matchCount;
    private final int misplacedCount;

    public Hint(int matchCount, int misplacedCount) {
        this.matchCount = matchCount;
        this.misplacedCount = misplacedCount;
    }

    public String format(){
        if (matchCount == 0 && misplacedCount == 0){
            return "없음";
        }
        return String.format("%d매치 %d틀림", matchCount, misplacedCount);
    }

    public int getMatchCount(){
        return matchCount;
    }

    public int getMisplacedCount(){
        return misplacedCount;
    }
}
