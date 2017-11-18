package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class EnvironmentAgent extends Agent{

    protected void setup() {

        System.out.println("Environment Agent");
        addBehaviour(new EnvironmentBehaviour(20));

    }

    protected void takeDown() {
        doDelete();
    }

    public class EnvironmentBehaviour extends Behaviour {

        private int duration;
        private int counter;

        public EnvironmentBehaviour(int duration) {
            this.duration = duration;
        }

        public void action() {

            System.out.println("Environment Behaviour");
            counter++;

        }

        public boolean done() {

            if(counter == duration)
                return true;

            return false;

        }

    }

}
