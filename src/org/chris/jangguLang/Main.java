package org.chris.jangguLang;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

//JangguLang 명령어 총정리
//#### 기본 수 표현법
//~ : -1을 나타냅니다.
//! : +1을 나타냅니다.
//
//#### 기본 명령어
//
//아니리 (아니리):
//입력받은 인덱스를 리셋합니다.
//아니리(첫 번째 인덱스)~(마지막 인덱스) 형태로 사용해 범위를 지정할 수 있으며, 범위의 값들을 리셋합니다.
//예: 아니리!!~!!!! → 2번부터 4번 인덱스까지 리셋.
//
//쿵 (쿵):
//스택의 0번째 값을 뒤에 오는 수만큼 추가하거나, 문자를 사용해 기존 값을 대체합니다.
//예: 쿵!!!! → 0번째 스택에 4 추가, 쿵¤ → 0번째 스택의 값이 문자 ¤로 대체됨.
//
//덩 (덩):
//0번째 스택의 값을 지정된 인덱스에 추가합니다.
//예: 덩!! → 0번째 스택의 값을 2번 인덱스에 추가.
//
//덕 (덕):
//지정된 인덱스의 값을 출력합니다.
//예: 덕!! → 2번 인덱스의 값을 출력.
//
//기덕 (기덕):
//지정된 인덱스의 값을 0번째 스택으로 가져옵니다.
//예: 기덕!! → 2번 인덱스의 값을 0번째 스택에 저장.
//
//더러러러 (더러러러):
//숫자 또는 문자를 입력받아 지정된 인덱스에 저장합니다.
//범위를 지정해 여러 인덱스에 걸쳐 입력받을 수 있습니다.
//예: 더러러러 !! ~ !!!!! → 2번부터 6번 인덱스에 입력받은 값 저장.
//
//얼씨구 (얼씨구):
//문자를 나타내는 명령어로, 쿵 명령어와 함께 사용해 0번 스택의 값을 해당 문자로 대체합니다.
//예: 쿵 얼씨구 좋....... ..다 → 0번 스택의 값이 'H'로 대체됨 (아스키 코드 72).
//
//#### 반복문 관련 명령어
//
//얼쑤 (얼쑤):
//반복문의 시작을 나타냅니다. 지정된 인덱스의 값이 0이 아니면 반복문을 시작합니다.
//예: 얼쑤!! → 2번 인덱스의 값이 0이 아니면 반복문 시작.
//
//잘한다 (잘한다):
//반복문의 끝을 나타냅니다. 이 지점에서 반복문의 시작 (얼쑤)으로 돌아가서 반복 조건을 다시 확인합니다.
//예: 잘한다 → 반복문 종료 또는 반복문 시작으로 돌아가기.
//
//에헤라디야 (에헤라디야):
//반복문 탈출 명령어로, 반복문 내부에서 이 명령어를 만나면 반복문을 즉시 빠져나갑니다.
//예: 에헤라디야 → 반복문 탈출.
//
//허먼 (허먼):
//반복문 강제 종료 명령어로, 이 명령어를 만나면 반복문을 즉시 종료하고 다음 명령어로 넘어갑니다.
//예: 허먼 → 반복문 강제 종료.
//예제 코드
//수정된 예제 코드입니다.
//
//쿵 더러러러 !! ~ !!!!!
//--------------------------------------------------------
//얼쑤!!
//    기덕!!
//    쿵 얼씨구 좋....... ..다  // 0번째 스택의 값을 'H'로 대체
//
//    기덕!!!
//    쿵 얼씨구 좋...... .....다  // 0번째 스택의 값을 'e'로 대체
//
//    허먼  // 반복문 강제 종료
//잘한다
//
//쿵 좋~다  // 반복문 강제 종료 후 실행할 명령어
//--------------------------------------------------------
//
//이 코드에서는 얼씨구 명령어가 문자를 나타내며, 이를 쿵 명령어와 함께 사용하여 0번 스택의 값을 문자로 대체합니다. 잘한다는 반복문의 끝을 나타내며, 허먼은 반복문을 강제 종료하는 데 사용됩니다.

public class Main {
    static boolean debug = false;

    public static void main(String[] args) {
        try {
            // Main 클래스의 경로를 URL에서 파일 객체로 변환
            File classFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            // 경로를 얻고 필요한 부분까지 잘라냄
            Path classPath = classFile.toPath();

            // 기준 경로를 바탕으로 상대 경로를 합쳐 새로운 파일 객체 생성
            File file = new File(classPath.toFile(), "org/chris/jangguLang/code/main.jangguLang");

            if (debug) {
                // 파일 존재 여부 확인
                if (file.exists()) {
                    System.out.println("파일이 존재합니다: " + file.getAbsolutePath());
                } else {
                    System.out.println("파일이 존재하지 않습니다.");
                    return;
                }
            }

            // 파일의 모든 내용을 바이트 배열로 읽어오기
            List<String> list = Files.readAllLines(file.toPath());

            if (debug) {
                // 파일 내용 출력
                for (String line : list) {
                    System.out.println(line);
                }
            }

            JangguList array = new JangguList();
            Scanner sc = new Scanner(System.in);

            for (int i = 0; i < list.size(); i++) {
                String line = list.get(i);
                line = removeComment(line);

                if (line.startsWith("더러러러")) {
                    if (line.startsWith("더러러러 ")) {
                        String line2 = line.substring(5);

                        int exclamationsBefore = 0;
                        int exclamationsAfter = 0;
                        boolean hasOtherCharacters = false;

                        // 문자열을 '~'로 분리
                        String[] parts = line2.split("~");

                        if (parts.length > 2) {
                            hasOtherCharacters = true;
                        }

                        if (parts.length > 0) {
                            exclamationsBefore = countNumber(parts[0]);
                            if (exclamationsBefore == -1) {
                                hasOtherCharacters = true;
                            }
                        }

                        if (parts.length > 1) {
                            exclamationsAfter = countNumber(parts[1]);
                            if (exclamationsAfter == -1) {
                                hasOtherCharacters = true;
                            }
                        }

                        if (hasOtherCharacters) {
                            System.out.println("Invalid command: " + line);
                            return;
                        }

                        if (debug) {
                            System.out.println("~ 앞에 있는 !의 개수: " + exclamationsBefore);
                            System.out.println("~ 뒤에 있는 !의 개수: " + exclamationsAfter);
                        }

                        String scan = sc.nextLine();
                        char[] tokens = scan.toCharArray();

                        for (int j = exclamationsBefore; j <= exclamationsAfter; j++) {
                            array.set(j, tokens[j-exclamationsBefore]);
                        }

                        if (debug) {
                            System.out.println(array);
                        }
                    } else {
                        System.out.println("Invalid command: " + line);
                        return;
                    }
                } else if (line.startsWith("아나리")) {
                    if (line.startsWith("아나리 ")) {
                        String line2 = line.substring(4);

                        int exclamationsBefore = 0;
                        int exclamationsAfter = 0;
                        boolean hasOtherCharacters = false;

                        // 문자열을 '~'로 분리
                        String[] parts = line2.split("~");

                        if (parts.length > 2) {
                            hasOtherCharacters = true;
                        }

                        if (parts.length > 0) {
                            exclamationsBefore = countNumber(parts[0]);
                            if (exclamationsBefore == -1) {
                                hasOtherCharacters = true;
                            }
                        }

                        if (parts.length > 1) {
                            exclamationsAfter = countNumber(parts[1]);
                            if (exclamationsAfter == -1) {
                                hasOtherCharacters = true;
                            }
                        }

                        if (hasOtherCharacters) {
                            System.out.println("Invalid command: " + line);
                            return;
                        }

                        if (debug) {
                            System.out.println("~ 앞에 있는 !의 개수: " + exclamationsBefore);
                            System.out.println("~ 뒤에 있는 !의 개수: " + exclamationsAfter);
                        }

                        for (int j = exclamationsBefore; j <= exclamationsAfter; j++) {
                            array.set(j, '0');
                        }

                        if (debug) {
                            System.out.println(array);
                        }
                    } else {
                        System.out.println("Invalid command: " + line);
                        return;
                    }
                } else if (line.startsWith("덩")) {
                    String line2 = line.substring(1);

                    int number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == -1) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        System.out.println("Invalid command: " + line);
                        return;
                    }

                    Object element = array.get(0);
                    array.set(number, array.isIndexChar(0)?(char) ((int) element):element);

                    if (debug) {
                        System.out.println(array);
                    }
                } else if (line.startsWith("쿵")) {
                    if (line.startsWith("쿵 얼씨구")) {
                        if (line.startsWith("쿵 얼씨구 ")) {
                            String line2 = line.substring(5);

                            int number = 0;
                            boolean hasOtherCharacters = false;

                            number = countNumber(line2);
                            if (number == -1) {
                                hasOtherCharacters = true;
                            }

                            if (hasOtherCharacters) {
                                System.out.println("Invalid command: " + line);
                                return;
                            }

                            array.set(0, (char) number);

                            if (debug) {
                                System.out.println(array);
                            }
                        } else {
                            System.out.println("Invalid command: " + line);
                            return;
                        }
                    } else {
                        String line2 = line.substring(1);

                        int number = 0;
                        boolean hasOtherCharacters = false;

                        number = countNumber(line2);
                        if (number == -1) {
                            hasOtherCharacters = true;
                        }

                        if (hasOtherCharacters) {
                            System.out.println("Invalid command: " + line);
                            return;
                        }

                        array.set(0, number);

                        if (debug) {
                            System.out.println(array);
                        }
                    }
                } else if (line.startsWith("덕")) {
                    String line2 = line.substring(1);

                    int number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == -1) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        System.out.println("Invalid command: " + line);
                        return;
                    }

                    Object element = array.get(number);
                    char ch = (char) ((int) element);
                    System.out.println(array.isIndexChar(number)?ch:element);
                } else if (line.startsWith("기덕")) {
                    String line2 = line.substring(2);

                    int number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == -1) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        System.out.println("Invalid command: " + line);
                        return;
                    }

                    array.set(0, array.get(number));
                    if (debug) {
                        System.out.println(array);
                    }
                } else if (line.startsWith("얼쑤")) {
                    String line2 = line.substring(2);

                    int number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == -1) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        System.out.println("Invalid command: " + line);
                        return;
                    }

                    if (((int) array.get(number)) == 0) {
                        while (true) {
                            if (!list.get(i).equals("잘한다")) {
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                } else if (line.startsWith("잘한다")) {
                    int j = 0;
                    while (true) {
                        if (!list.get(i).startsWith("얼쑤")) {
                            i--;
                            if (list.get(i).equals("잘한다")) {
                                j++;
                            }
                        } else if (j != 0) {
                            j--;
                            i--;
                        } else {
                            i--;
                            break;
                        }
                    }
                } else if (line.startsWith("에헤라디야")) {
                    int j = 0;
                    while (true) {
                        if (!list.get(i).startsWith("잘한다")) {
                            i++;
                            if (list.get(i).equals("얼쑤")) {
                                j++;
                            }
                        } else if (j != 0) {
                            j--;
                            i++;
                        } else {
                            break;
                        }
                    }
                }
                // 좋다 빼고 완성
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static int countNumber(String s) {
        int number = 0;
        if (s.startsWith(" 좋") && s.endsWith("다")) {
            s = s.substring(2, s.length() - 1);
            String[] parts = s.split(" ");
            for (String part : parts) {
                number *= 10;
                if (!part.equals("~")) {
                    for (char c : part.toCharArray()) {
                        if (c == '!') {
                            number++;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        } else {
            for (char c : s.toCharArray()) {
                if (c == '!') {
                    number++;
                } else {
                    return -1;
                }
            }
        }
        return number;
    }

    public static String removeComment(String str) {
        int index = str.indexOf("//");
        if (index != -1) {
            return str.substring(0, index).trim();  // "//" 이전 부분만 자르고 공백 제거
        }
        return str;  // "//"가 없을 경우 원본 문자열 반환
    }
}
