package ru.nsu.aeliseev2.task211;

/**
 * An implementation of {@code PrimeChecker} that splits the array into several slices and runs
 * the check for each slice on a separate thread.
 */
public class ThreadedPrimeChecker extends PrimeChecker {
    private final int numThreads;

    /**
     * Initializes a new instance of {@code ThreadedPrimeChecker} with the specified number of
     * worker threads created at each {@code hasComposites} call.
     *
     * @param numThreads The number of threads to use.
     */
    public ThreadedPrimeChecker(int numThreads) {
        if (numThreads < 1) {
            throw new IllegalArgumentException("numThreads must be at least 1");
        }
        this.numThreads = numThreads;
    }

    static class PrimeWorker implements Runnable {
        static class Result {
            private boolean value;
            private int workersLeft;

            public Result(int numWorkers) {
                value = false;
                workersLeft = numWorkers;
            }

            public void setResult(boolean value) {
                synchronized (this) {
                    this.value |= value;
                    this.workersLeft -= 1;
                    this.notify();
                }
            }

            public void waitCompletion() throws InterruptedException {
                synchronized (this) {
                    while (workersLeft > 0 && !value) {
                        this.wait();
                    }
                }
            }

            public boolean getValue() {
                return value;
            }
        }

        private final long[] numbers;
        private final int startIndex;
        private final int endIndex;
        private final Result result;

        public PrimeWorker(long[] numbers, int startIndex, int endIndex, Result result) {
            this.numbers = numbers;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.result = result;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < endIndex; i++) {
                if (!isPrime(numbers[i])) {
                    result.setResult(true);
                    return;
                }
                if (Thread.interrupted()) {
                    return;
                }
            }
            result.setResult(false);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasComposites(long[] numbers) {
        final int numPerThread = numbers.length / numThreads;
        final int numExtra = numbers.length % numThreads;

        var result = new PrimeWorker.Result(numThreads);

        Thread[] threads = new Thread[numThreads];
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            int numStart = numPerThread * threadIndex;
            int numEnd = numStart + numPerThread + (threadIndex == numThreads - 1 ? numExtra : 0);

            var worker = new PrimeWorker(numbers, numStart, numEnd, result);
            threads[threadIndex] = new Thread(worker);
            threads[threadIndex].start();
        }

        try {
            result.waitCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            threads[threadIndex].interrupt();
        }
        return result.getValue();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return String.format("ThreadedPrimeChecker(%d)", numThreads);
    }
}
