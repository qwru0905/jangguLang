package org.chris.jangguLang;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeExcuter {
    private InputOutputHandler ioHandler;
    private final boolean debug;
    
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
        JangguList array = new JangguList();

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

                int number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == -1) {
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
                    if (line.startsWith("쿵 얼씨구 ")) {
                        String line2 = line.substring(5);

                        int number = 0;
                        boolean hasOtherCharacters = false;

                        number = countNumber(line2);
                        if (number == -1) {
                            hasOtherCharacters = true;
                        }

                        if (hasOtherCharacters) {
                            ioHandler.printOutput("Invalid command: " + line);
                            return;
                        }

                        array.set(0, (char) number);

                        if (debug) {
                            ioHandler.printOutput(array.toString());
                        }
                    } else {
                        ioHandler.printOutput("Invalid command: " + line);
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

                int number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == -1) {
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

                int number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == -1) {
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

                int number = 0;
                boolean hasOtherCharacters = false;

                number = countNumber(line2);
                if (number == -1) {
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
            }
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
