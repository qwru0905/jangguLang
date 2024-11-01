package org.chris.jangguLang;

import java.util.List;

public class CodeExcuter extends Thread {
    private final InputOutputHandler ioHandler;
    private final boolean debug;
    private final JangguList array = new JangguList();
    private volatile boolean stopRequested = false;

    public void requestStop() {
        stopRequested = true;
        this.interrupt();  // 중단 신호 전달
    }

    public void restartExecution(String code) {
        if (isAlive()) {
            requestStop();
            try {
                join();  // 스레드가 종료될 때까지 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        stopRequested = false;  // 플래그 재설정
        start();  // 스레드 다시 시작
        codeExecute(code);
    }
    
    public CodeExcuter(InputOutputHandler ioHandler) {
        this.ioHandler = ioHandler;
        this.debug = false;
    }
    
    public CodeExcuter(InputOutputHandler ioHandler, boolean debug) {
        this.ioHandler = ioHandler;
        this.debug = debug;
    }
    
    public void codeExecute(String code) {
        List<String> list = List.of(code.split("\n"));
        codeExecute(list);
    }

    public void codeExecute(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (stopRequested) return;
            String line = list.get(i);
            line = removeComment(line);

            if (line.startsWith("더러러러")) {
                if (line.startsWith("더러러러 ")) {
                    String line2 = line.substring(5);

                    Integer exclamationsBefore = 0;
                    Integer exclamationsAfter = 0;
                    boolean hasOtherCharacters = false;

                    // 문자열을 '~'로 분리
                    String[] parts = line2.split("-");

                    if (parts.length > 2) {
                        hasOtherCharacters = true;
                    }

                    if (parts.length > 0) {
                        exclamationsBefore = countNumber(parts[0]);
                        if (exclamationsBefore == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (parts.length > 1) {
                        exclamationsAfter = countNumber(parts[1]);
                        if (exclamationsAfter == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    if (debug) {
                        ioHandler.printOutput("~ 앞에 있는 !의 개수: " + exclamationsBefore);
                        ioHandler.printOutput("~ 뒤에 있는 !의 개수: " + exclamationsAfter);
                    }

                    String scan = ioHandler.getInput();
                    char[] tokens = scan.toCharArray();

                    for (int j = exclamationsBefore; j <= exclamationsAfter; j++) {
                        array.set(j, tokens[j - exclamationsBefore]);
                    }

                    if (debug) {
                        ioHandler.printOutput(array.toString());
                    }
                } else {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }
            } else if (line.startsWith("아나리")) {
                if (line.startsWith("아나리 ")) {
                    String line2 = line.substring(4);

                    Integer exclamationsBefore = 0;
                    Integer exclamationsAfter = 0;
                    boolean hasOtherCharacters = false;

                    // 문자열을 '~'로 분리
                    String[] parts = line2.split("-");

                    if (parts.length > 2) {
                        hasOtherCharacters = true;
                    }

                    if (parts.length > 0) {
                        exclamationsBefore = countNumber(parts[0]);
                        if (exclamationsBefore == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (parts.length > 1) {
                        exclamationsAfter = countNumber(parts[1]);
                        if (exclamationsAfter == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    if (debug) {
                        ioHandler.printOutput("~ 앞에 있는 !의 개수: " + exclamationsBefore);
                        ioHandler.printOutput("~ 뒤에 있는 !의 개수: " + exclamationsAfter);
                    }

                    for (int j = exclamationsBefore; j <= exclamationsAfter; j++) {
                        array.set(j, 0);
                    }

                    if (debug) {
                        ioHandler.printOutput(array.toString());
                    }
                } else {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }
            } else if (line.startsWith("덩")) {
                String line2 = line.substring(1);

                Integer number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == null) {
                    hasOtherCharacters = true;
                }

                if (hasOtherCharacters) {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }

                Object element = array.get(0);
                array.set(number, array.isIndexChar(0) ? (char) ((int) element) : element);

                if (debug) {
                    ioHandler.printOutput(array.toString());
                }
            } else if (line.startsWith("쿵")) {
                if (line.startsWith("쿵 얼씨구")) {
                    String line2 = line.substring(5);

                    Integer number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == null) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    array.set(0, (char) number.intValue());

                    if (debug) {
                        ioHandler.printOutput(array.toString());
                    }
                } else {
                    String line2 = line.substring(1);

                    Integer number = 0;
                    boolean hasOtherCharacters = false;

                    number = countNumber(line2);
                    if (number == null) {
                        hasOtherCharacters = true;
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    array.set(0, number);

                    if (debug) {
                        ioHandler.printOutput(array.toString());
                    }
                }
            } else if (line.startsWith("덕")) {
                String line2 = line.substring(1);

                Integer number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == null) {
                    hasOtherCharacters = true;
                }

                if (hasOtherCharacters) {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }

                Object element = array.get(number);
                char ch = (char) ((int) element);
                ioHandler.printOutput(array.isIndexChar(number) ? String.valueOf(ch) : String.valueOf((int) element));
            } else if (line.startsWith("기덕")) {
                String line2 = line.substring(2);

                Integer number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == null) {
                    hasOtherCharacters = true;
                }

                if (hasOtherCharacters) {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }

                array.set(0, array.get(number));
                if (debug) {
                    ioHandler.printOutput(array.toString());
                }
            } else if (line.startsWith("얼쑤")) {
                String line2 = line.substring(2);

                Integer number;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == null) {
                    hasOtherCharacters = true;
                }

                if (hasOtherCharacters) {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }

                if (((int) array.get(number)) == 0) {
                    while (true) {
                        if (!list.get(i).startsWith("잘한다")) {
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
                        if (list.get(i).startsWith("잘한다")) {
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
                        if (list.get(i).startsWith("얼쑤")) {
                            j++;
                        }
                    } else if (j != 0) {
                        j--;
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (line.startsWith("더해라")) {
                if (line.startsWith("더해라 ")) {
                    String line2 = line.substring(4);

                    Integer firstIndex = 0;
                    Integer secondIndex = 0;
                    boolean hasOtherCharacters = false;

                    // 문자열을 '~'로 분리
                    String[] parts = line2.split("-");

                    if (parts.length > 2) {
                        hasOtherCharacters = true;
                    }

                    if (parts.length > 0) {
                        firstIndex = countNumber(parts[0]);
                        if (firstIndex == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (parts.length > 1) {
                        secondIndex = countNumber(parts[1]);
                        if (secondIndex == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    if (debug) {
                        ioHandler.printOutput("~ 앞에 있는 !의 개수: " + firstIndex);
                        ioHandler.printOutput("~ 뒤에 있는 !의 개수: " + secondIndex);
                    }

                    if (array.isIndexChar(firstIndex) || array.isIndexChar(secondIndex)) {
                        ioHandler.printOutput("It can't be char: " + line);
                        return;
                    }

                    int firstIndexNumber = (int) array.get(firstIndex);
                    int secondIndexNumber = (int) array.get(secondIndex);
                    array.set(0, firstIndexNumber + secondIndexNumber);
                } else {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }
            } else if (line.startsWith("빼라")) {
                if (line.startsWith("빼라 ")) {
                    String line2 = line.substring(3);

                    Integer firstIndex = 0;
                    Integer secondIndex = 0;
                    boolean hasOtherCharacters = false;

                    // 문자열을 '~'로 분리
                    String[] parts = line2.split("-");

                    if (parts.length > 2) {
                        hasOtherCharacters = true;
                    }

                    if (parts.length > 0) {
                        firstIndex = countNumber(parts[0]);
                        if (firstIndex == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (parts.length > 1) {
                        secondIndex = countNumber(parts[1]);
                        if (secondIndex == null) {
                            hasOtherCharacters = true;
                        }
                    }

                    if (hasOtherCharacters) {
                        ioHandler.printOutput("Invalid command: " + line);
                        return;
                    }

                    if (debug) {
                        ioHandler.printOutput("~ 앞에 있는 !의 개수: " + firstIndex);
                        ioHandler.printOutput("~ 뒤에 있는 !의 개수: " + secondIndex);
                    }

                    if (array.isIndexChar(firstIndex) || array.isIndexChar(secondIndex)) {
                        ioHandler.printOutput("It can't be char: " + line);
                        return;
                    }

                    int firstIndexNumber = (int) array.get(firstIndex);
                    int secondIndexNumber = (int) array.get(secondIndex);
                    array.set(0, firstIndexNumber - secondIndexNumber);
                } else {
                    ioHandler.printOutput("Invalid command: " + line);
                    return;
                }
            }
        }
        requestStop();
    }

    public Integer countNumber(String s) {
        int number = 0;
        if (s.startsWith(" 좋") && s.endsWith("다")) {
            s = s.substring(2, s.length() - 1);
            String[] parts = s.split(" ");
            boolean isNumberMinus = false;
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (i == 0 && part.equals("~")) {
                    isNumberMinus = true;
                }
                number *= 10;
                if (!part.equals("~")) {
                    for (char c : part.toCharArray()) {
                        if (c == '!') {
                            number++;
                        } else {
                            return null;
                        }
                    }
                }
            }
            if (isNumberMinus) {
                number = -number;
            }
        } else if (s.startsWith(" 기")) {
            s = s.substring(2);
            for (char c : s.toCharArray()) {
                if (c == '!') {
                    number++;
                } else if (c == '~') {
                    number--;
                } else {
                    return null;
                }
            }
            return array.isIndexChar(number) ? null : (int) array.get(number);
        } else {
            for (char c : s.toCharArray()) {
                if (c == '!') {
                    number++;
                } else if (c == '~') {
                    number--;
                } else {
                    return null;
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
