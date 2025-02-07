package code.seeds;

import code.seeds.domain.Hint;
import code.seeds.domain.Word;
import java.util.Scanner;

public class Main {

    // 전역 필드는 String 타입으로 유지합니다.
    private final String computerWord;

    public Main() {
        computerWord = RandomWordGenerator.getRandomWord();
    }

    // 테스트용 생성자. 삭제 금지.
    public Main(String fixedWord) {
        this.computerWord = fixedWord;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Main().run(scanner);
        scanner.close();
    }

    public void run(Scanner scanner) {
        System.out.println("단어 맞추기 게임을 시작합니다.");

        Word answer = Word.of(computerWord);

        while (true) {
            System.out.print("단어를 입력해주세요: ");
            String input = scanner.nextLine();

            Word guess = Word.of(input);

            if (answer.getValue().equals(guess.getValue())) {
                System.out.println("정답입니다! 게임 종료");
                break;
            }


            Hint hint = answer.compareWith(guess);
            System.out.println(hint.format());
        }


        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        String restartInput = scanner.nextLine().trim();
        if ("1".equals(restartInput)) {
            run(scanner);
        } else if (!"2".equals(restartInput)) {
            throw new IllegalArgumentException("1 또는 2");
        }
    }
}
