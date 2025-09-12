package ru.nsu.aeliseev2.task131.tests;

import java.io.Reader;

class TestReader extends Reader {
    private final TestReaderSection[] sections;
    int sectionIndex = 0;
    long indexInSection = 0;

    public TestReader(TestReaderSection... sections) {
        this.sections = sections;
    }

    @Override
    public int read(char[] chars, int startIndex, int count) {
        int readCount = 0;
        for (int i = startIndex; i < startIndex + count; i++) {
            int ch = read();
            if (ch == -1) {
                return -1;
            }
            chars[i] = (char) ch;
            readCount++;
        }
        return readCount;
    }

    @Override
    public int read() {
        if (sectionIndex >= sections.length) {
            return -1;
        }
        for (; sectionIndex < sections.length; sectionIndex++) {
            if (indexInSection < sections[sectionIndex].totalLength) {
                break;
            }
            indexInSection = 0;
        }
        if (sectionIndex >= sections.length) {
            return -1;
        }
        return sections[sectionIndex].get(indexInSection++);
    }

    @Override
    public void close() {
    }
}
