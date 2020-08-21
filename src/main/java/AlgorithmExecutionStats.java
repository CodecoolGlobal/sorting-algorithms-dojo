public class AlgorithmExecutionStats {
    private final long start;
    private final long end;

    public AlgorithmExecutionStats(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long calculateAlgorithmExecutionTime() {
        return end - start;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
