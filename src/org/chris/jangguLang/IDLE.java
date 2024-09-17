package org.chris.jangguLang;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        JMenuItem item2 = new JMenuItem("저장");
        JMenuItem item3 = new JMenuItem("새 파일");
        JMenuItem item4 = new JMenuItem("코드 실행");  // 코드 실행 메뉴 추가

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

        // 코드 실행 버튼 기능 추가
        item4.addActionListener(e -> {
            String code = textArea.getText();
            try {
                // Main 클래스의 경로를 URL에서 파일 객체로 변환
                File classFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

                // 경로를 얻고 필요한 부분까지 잘라냄
                Path classPath = classFile.toPath();

                // 기준 경로를 바탕으로 상대 경로를 합쳐 새로운 파일 객체 생성
                File file = new File(classPath.toFile(), "org/chris/jangguLang/code/main.jangguLang");

                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                fileWriter.write(code);
                fileWriter.flush();
                fileWriter.close();

                // 현재 실행 중인 클래스의 경로를 얻기
                Path basePath = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

                Process process = Runtime.getRuntime().exec("java -cp " + basePath + " org.chris.jangguLang.Main");

                // ErrorStream을 읽어서 에러 메시지를 확인
                InputStream errorStream = process.getErrorStream();
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                String errorLine;
                //while ((errorLine = errorReader.readLine()) != null) {
                //    System.err.println("Error: " + errorLine);
                //}

                // 프로세스의 입력 스트림 및 출력 스트림 설정
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

                // 스레드를 사용해 입력 스트림을 읽고 출력에 반영
                new Thread(() -> {
                    try {
                        String line;
                        StringBuilder output = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                            outputTextArea.setText(output.toString());  // 출력 갱신
                        }
                    } catch (IOException ex) {
                        outputTextArea.setText("입력/출력 중 오류 발생: " + ex.getMessage());
                    }
                }).start();

                // 입력 필드에서 입력을 받을 때마다 프로세스에 전달
                inputField.addActionListener(inputEvent -> {
                    try {
                        String userInput = inputField.getText();
                        writer.write(userInput + "\n");  // 입력 값을 프로세스에 전달
                        writer.flush();
                        inputField.setText("");  // 입력 필드 비우기
                    } catch (IOException ex) {
                        outputTextArea.setText("입력 전달 중 오류 발생: " + ex.getMessage());
                    }
                });
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 화면에 표시
        frame.setVisible(true);
    }
}
