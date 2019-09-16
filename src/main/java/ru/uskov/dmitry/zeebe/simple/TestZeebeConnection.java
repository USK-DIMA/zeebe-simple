package ru.uskov.dmitry.zeebe.simple;

public class TestZeebeConnection {
    public static void main(String[] args) {
        ZeebeClientUtil.executeCommand(c -> {
            System.out.println("Test connection");
        });
    }
}
