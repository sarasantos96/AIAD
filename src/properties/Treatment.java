package properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Treatment {
    Acupunture(12000),
    Adenosine(6000),
    Allergy_shots(1000),
    Homocysteine(16000),
    Sclerotherapy(8000),
    Keratecttomy(7000),
    MCH(2000),
    Microdermabrasion(4500),
    Vasectomy(6400),
    X_Ray(3300),
    Joint_Injection(7500),
    Laparoscopy(4000);




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
