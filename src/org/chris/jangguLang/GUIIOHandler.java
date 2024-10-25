package org.chris.jangguLang;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GUIIOHandler extends Thread implements InputOutputHandler {
    private JTextField inputField;
    private JTextPane outputArea; // JTextArea 대신 JTextPane 사용
    private volatile String input = null;

    public GUIIOHandler(JTextField inputField, JTextPane outputArea) {
        this.inputField = inputField;
        this.outputArea = outputArea;

        // ActionListener로 Enter 키를 누를 때 입력 처리
        inputField.addActionListener(e -> {
            // 비동기로 입력 처리
            handleInput();
        });
    }

    private void handleInput() {
        appendOutput(inputField.getText() + "\n", Color.GREEN);
        input = inputField.getText();
        inputField.setText("");
    }

    private void appendOutput(String message, Color color) {
        StyledDocument doc = outputArea.getStyledDocument();
        Style style = outputArea.addStyle("Style", null);
        StyleConstants.setForeground(style, color);

        try {
            // 이전 메시지와 연결하여 한 줄로 이어 붙입니다.
            doc.insertString(doc.getLength(), message, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInput() {
        while (input == null) {
            onSpinWait();
        }
        String result = input;  // 입력된 값을 반환
        input = null;           // 다음 입력을 위해 초기화
        return result;
    }

    @Override
    public void printOutput(String output) {
        appendOutput(output, Color.WHITE); // 기본 출력은 검정색으로
    }
}
