package org.chris.jangguLang;

import org.chris.jangguLang.code.Data;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.logging.Level;

public class GUIIOHandler extends Thread implements InputOutputHandler {
    private final JTextField inputField;
    private final JTextPane outputArea; // JTextArea 대신 JTextPane 사용
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
            Data.getInstance().getLogger().log(Level.SEVERE, "Native library load failed: " + e.getMessage(), e);
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

    @Override
    public void run() {

    }
}
