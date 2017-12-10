package properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Disease {
    Myositis(1,5),
    Lung_cancer(50,3),
    Tonsillitis(32,2),
    Diabetes(4,8),
    Hepatitis_C(8,9),
    Kawasaki_disease(49,23),
    Siderosis(24,24),
    Narcolepsy(1,1),
    Zygomycosis(13,7),
    Keratitis(1,20),
    Myxedema(7,6);




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
