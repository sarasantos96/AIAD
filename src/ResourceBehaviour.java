import jade.core.behaviours.SimpleBehaviour;

public class ResourceBehaviour extends SimpleBehaviour{
    private int t = 0;
    @Override
    public void action() {
        System.out.println(t);
        t++;
    }

    @Override
    public boolean done() {

        return t == 5;
    }
}
