package org.chris.jangguLang;

import java.util.Scanner;

public class ConsoleIOHandler implements InputOutputHandler {
    private Scanner sc = new Scanner(System.in);

    @Override
    public String getInput() {
        return sc.nextLine();
    }

    @Override
    public void printOutput(String output) {
        System.out.print(output);
    }
}
