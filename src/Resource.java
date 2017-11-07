import jade.core.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class Resource extends Agent{
    private String name;
    private ArrayList<String> available_treatments;
    private ArrayList<Pair<Integer,Patient>> reserves;

    protected void setup() {
        System.out.println("Resource agent " + getAID().getName() + " is ready.");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            this.name = (String) args[0];
            System.out.println("Resource name is " + this.name);
            //TODO Definir como vão ser passados os tratamentos disponíveis e os pares
        }else{
            System.out.println("No resource specified");
            doDelete();
        }
    }

    protected void takeDown() {
        System.out.println("Resource agent " + getAID().getName() + " is terminating");
    }
}
