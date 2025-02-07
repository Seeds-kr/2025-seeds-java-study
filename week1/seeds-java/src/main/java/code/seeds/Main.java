package code.seeds;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private String computerWord;

    public Main(){
        Random ran = new Random();
        computerWord = RandomWordGenerator.getRandomWord();
    }

    public Main(String fixedWord){  // 테스트용 생성자. 삭제 금지.
        this.computerWord = fixedWord;
    }

    public static void main(String[] args) {
       // System.out.println("단어 맞추기 게임을 시작합니다!.");
        Scanner scanner = new Scanner(System.in);
        new Main().run(scanner);
        scanner.close();
    }

    public void run(Scanner scanner) {
        System.out.println("단어 맞추기 게임을 시작합니다.");
        while (true) {
            System.out.print("단어를 입력해주세요: ");
            String input = scanner.next();

            if (input.length() != computerWord.length() || !input.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("5글자 알파벳으로 된 단어를 입력하세요.");
            }

            String answerTemp = computerWord;
            int match = 0;
            int wrong = 0;

            for (int i = 0; i < answerTemp.length(); i++) {
                if (input.substring(i, i + 1).equals(answerTemp.substring(i, i + 1))) {
                    match++;
                    answerTemp = answerTemp.substring(0, i) + "1" + answerTemp.substring(i + 1);
                    input = input.substring(0, i) + "2" + input.substring(i + 1);
                }
            }

            for (int i = 0; i < answerTemp.length(); i++) {
                for (int j = 0; j < answerTemp.length(); j++) {
                    if (input.substring(i, i + 1).equals(answerTemp.substring(j, j + 1))) {
                        wrong++;
                        answerTemp = answerTemp.substring(0, j) + "1" + answerTemp.substring(j + 1);
                        break;
                    }
                }
            }

            if (match != 0) {
                System.out.print(match + "매치 ");
            }
            if (wrong != 0) {
                System.out.print(wrong + "틀림 ");
            }
            System.out.println();

            if (match == 5) {
                System.out.println("정답입니다! 게임 종료");
                System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                int num = scanner.nextInt();

                if (num == 2) {
                    break;
                } else if (num == 1) {
                    computerWord = RandomWordGenerator.getRandomWord();
                }
                else {
                    throw new IllegalArgumentException("1 또는 2를 입력하세요.");
                }
            }
        }
        // 컴퓨터 랜덤 단어 생성은 RandomWordGenerator.getRandomWord(); 를 이용한다.
    }
}
