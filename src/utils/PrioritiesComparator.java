package utils;

import jade.core.AID;
import javafx.util.Pair;

import java.util.Comparator;

public class PrioritiesComparator implements Comparator<Pair<AID, Double>> {
    @Override
    public int compare(Pair<AID, Double> o1, Pair<AID, Double> o2) {
        if(o1.getValue() > o2.getValue()){
            return -1;
        }
        else{
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
