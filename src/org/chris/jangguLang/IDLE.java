package org.chris.jangguLang;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class IDLE {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("장구랭");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // 다크모드 색상 설정
        Color bgColor = new Color(45, 45, 45);  // 어두운 배경색
        Color textColor = new Color(230, 230, 230);  // 밝은 텍스트 랑 경계선 색상
        Color barColor = new Color(70, 70, 70);  // 바 색상

        // 상단 메뉴 바 생성
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        fileMenu.setForeground(textColor);  // 메뉴 텍스트 색상
        menuBar.setBackground(barColor);  // 메뉴 바 배경색
        menuBar.setBorder(BorderFactory.createLineBorder(textColor));  // 메뉴 바 경계선

        // 파일 메뉴의 하위 항목 (코드 실행 항목 추가)
        JMenuItem item1 = new JMenuItem("열기");
        JMenuItem item2 = new JMenuItem("새 파일");
        JMenuItem item3 = new JMenuItem("저장");
        JMenuItem item4 = new JMenuItem("다른 이름으로 저장");

        // 메뉴 아이템 색상 설정
        for (JMenuItem item : new JMenuItem[]{item1, item2, item3, item4 }) {
            item.setBackground(barColor);
            item.setForeground(textColor);
        }


        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
        fileMenu.add(item4);
        menuBar.add(fileMenu);

        JMenu codeStartMenu = new JMenu("코드 실행");
        codeStartMenu.setForeground(textColor);
        menuBar.add(codeStartMenu);

        frame.setJMenuBar(menuBar);

        // 텍스트 입력을 위한 TextArea
        JTextArea textArea = new JTextArea();
        textArea.setBackground(bgColor);
        textArea.setForeground(textColor);
        textArea.setBorder(BorderFactory.createLineBorder(textColor));

        // 스크롤을 추가해 스크롤 가능한 텍스트 영역으로 설정
        JScrollPane textScrollPane = new JScrollPane(textArea);

        // 출력 공간의 상단 (출력 공간 제목)
        JLabel outputTitle = new JLabel("출력 공간");
        outputTitle.setHorizontalAlignment(SwingConstants.CENTER);
        outputTitle.setForeground(textColor);
        outputTitle.setOpaque(true);
        outputTitle.setBackground(barColor);
        outputTitle.setBorder(BorderFactory.createLineBorder(textColor));

        // 출력 공간의 하단 (실제 출력 텍스트)
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);  // 출력 텍스트는 편집 불가
        outputTextArea.setBackground(bgColor);
        outputTextArea.setForeground(textColor);
        outputTextArea.setBorder(BorderFactory.createLineBorder(textColor));

        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        // 출력에 입력을 받을 수 있는 입력 필드 추가
        JTextField inputField = new JTextField();
        inputField.setBackground(barColor);
        inputField.setForeground(textColor);
        inputField.setBorder(BorderFactory.createLineBorder(textColor));

        // 입력 필드와 출력 텍스트 영역을 포함하는 패널
        JPanel inputOutputPanel = new JPanel(new BorderLayout());
        inputOutputPanel.setBackground(bgColor);
        inputOutputPanel.add(outputTitle, BorderLayout.NORTH);
        inputOutputPanel.add(outputScrollPane, BorderLayout.CENTER);
        inputOutputPanel.add(inputField, BorderLayout.SOUTH);

        // JSplitPane을 사용해 텍스트 입력 영역(2/3)과 출력 영역(1/3)을 나누기
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textScrollPane, inputOutputPanel);
        splitPane.setResizeWeight(0.66);  // 상단 영역이 2/3, 하단 영역이 1/3 차지
        splitPane.setDividerSize(2);  // 경계선의 크기 설정

        // 컴포넌트 배치
        frame.add(splitPane, BorderLayout.CENTER);

        codeStartMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String code = textArea.getText();
                outputTextArea.setText("");
                InputOutputHandler ioHandler = new GUIIOHandler(inputField, outputTextArea);
                CodeExcuter excuter = new CodeExcuter(ioHandler);
                excuter.codeExecute(code);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        // JMenuItem item1 = new JMenuItem("열기");
        // JMenuItem item2 = new JMenuItem("새 파일");
        // JMenuItem item3 = new JMenuItem("저장");
        // JMenuItem item4 = new JMenuItem("다른 이름으로 저장");
        AtomicReference<File> fileRef = new AtomicReference<>(null);

        item1.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // 파일 필터 추가
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JangguLang Code Files", "jangguLang");
            fileChooser.setFileFilter(filter);

            // 파일 선택 다이얼로그를 보여줍니다.
            int result = fileChooser.showOpenDialog(frame);

            // 파일을 선택했을 때 처리
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                fileRef.set(file);
                frame.setTitle("장구랭 - " + file.getName());
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    StringBuilder content = new StringBuilder();

                    // 파일의 모든 줄을 읽어 TextArea에 넣음
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    textArea.setText(content.toString()); // TextArea에 텍스트 설정
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        item4.addActionListener(e -> {
            File file1 = fileRef.get();
            File file = anotherNameSave(textArea.getText(), frame, file1);
            if (file == null) return;
            frame.setTitle("장구랭 - " + file.getName());
            fileRef.set(file);
        });

        item2.addActionListener(e -> {
            fileRef.set(null);
            frame.setTitle("장구랭");
            textArea.setText("");
        });

        item3.addActionListener(e -> {
            File file1 = fileRef.get();
            if (file1 == null) {
                File file = anotherNameSave(textArea.getText(), frame, null);
                if (file == null) return;
                frame.setTitle("장구랭 - " + file.getName());
                fileRef.set(file);
            } else {
                // 파일을 저장(덮어쓰기 또는 새로 생성)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                    writer.write(textArea.getText()); // TextArea의 내용을 파일에 저장
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 화면에 표시
        frame.setVisible(true);
    }

    private static File anotherNameSave(String code, Frame frame, File file) {
        JFileChooser fileChooser = new JFileChooser();

        if (file == null) fileChooser.setSelectedFile(new File("main.jangguLang"));
        else fileChooser.setSelectedFile(file);

        // 저장 다이얼로그를 열어서 새 파일을 지정하거나 기존 파일을 선택하게 함
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // 파일을 저장(덮어쓰기 또는 새로 생성)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(code); // TextArea의 내용을 파일에 저장
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return fileToSave;
        }
        return null;
    }
}
