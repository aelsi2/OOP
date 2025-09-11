package ru.nsu.aeliseev2.task131.tests;

class TestReaderSection {
    final char[] chars;
    final long totalLength;

    public TestReaderSection(String content, long length) {
        chars = content.toCharArray();
        totalLength = length;
    }

    public TestReaderSection(String content) {
        chars = content.toCharArray();
        totalLength = chars.length;
    }

    public char get(int index) {
        return chars[index % chars.length];
    }
}
