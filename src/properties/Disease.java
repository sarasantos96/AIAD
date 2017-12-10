package properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Disease {
    DISEASE1(1,2),
    DISEASE2(2,2),
    DISEASE3(32,2);

    private int severity;
    private int criticality;


     Disease(int severity, int criticality){
        this.severity = severity;
        this.criticality = criticality;
    }

    public int getSeverity() {
        return severity;
    }

    public int getCriticality() {
        return criticality;
    }
    private static final List<Disease> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Disease randomDisease()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
