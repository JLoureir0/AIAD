package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class FarmAgent extends Agent {

    private static String farmNo;
    private static double ax, bx, cx;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 4) {

            System.out.println("Incorrect arguments");
            takeDown();
            return;

        }

        farmNo = args[0].toString();

        ax = Double.parseDouble(args[1].toString());
        bx = Double.parseDouble(args[2].toString());
        cx = Double.parseDouble(args[3].toString());

        System.out.println("Farm" + farmNo + " Agent");
        addBehaviour(new FarmWaterManagement(20));

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x) {
        return ax*x*x+bx*x+cx;
    }

    public class FarmWaterManagement extends Behaviour {

        private int duration;
        private int counter;

        public FarmWaterManagement(int duration) {
            this.duration = duration;
        }

        public void action() {

            System.out.println("Farm" + farmNo + " Management");
            counter++;

        }

        public boolean done() {

            if(counter == duration)
                return true;

            return false;

        }

    }

}
