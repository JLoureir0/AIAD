package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class FarmAgent extends Agent {

    private static String farmNo;

    protected void setup() {
        Object[] args = getArguments();

        farmNo = args[0].toString();

        System.out.println("Farm" + farmNo + " Agent");
        addBehaviour(new FarmWaterManagement(20));
    }

    protected void takeDown() {

    }

    public class FarmWaterManagement extends Behaviour {

        private int duration;
        private int counter;

        public FarmWaterManagement(int duration)
        {
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
