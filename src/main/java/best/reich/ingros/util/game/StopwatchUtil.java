package best.reich.ingros.util.game;

/**
 * @author fluffycq
 */


// hollow i am not servant i tell you how to fix ca -_-
// fluffy why are you so cute

public class StopwatchUtil {
    private static long previousMS;

    public StopwatchUtil() {
        reset();
    }

    public static boolean hasCompleted(long milliseconds) {
        return (getCurrentMS() - previousMS >= milliseconds);
    }

    public void reset() {
        this.previousMS = getCurrentMS();
    }

    public long getPreviousMS() {
        return this.previousMS;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
}