package org.chris.jangguLang;

import javax.swing.*;

public class GUIIOHandler implements InputOutputHandler {
    private JTextField inputField;
    private JTextArea outputArea;
    private String input = null;

    public GUIIOHandler(JTextField inputField, JTextArea outputArea) {
        this.inputField = inputField;
        this.outputArea = outputArea;

        // ActionListener로 Enter 키를 누를 때 입력 처리
        inputField.addActionListener(e -> {
            synchronized (this) {
                input = inputField.getText();  // 입력된 텍스트를 가져옴
                inputField.setText("");        // 입력 필드를 초기화
                notify();                      // 대기 중인 스레드를 깨움
            }
        });
    }

    @Override
    public String getInput() {
        while (input == null) {
            try {
                wait();  // Enter 키 입력이 될 때까지 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = input;  // 입력된 값을 반환
        input = null;           // 다음 입력을 위해 초기화
        return result;
    }

    @Override
    public void printOutput(String output) {
        outputArea.append(output);
    }
}
