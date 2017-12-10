package properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Treatment {
    test(20000),
    test2(30000);


    private long duration;

    Treatment(long duration){
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    private static final List<Treatment> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Treatment randomTreatment()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
