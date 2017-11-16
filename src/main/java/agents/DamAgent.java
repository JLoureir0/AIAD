package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class DamAgent extends Agent {

    protected void setup() {

        System.out.println("Dam Agent");
        addBehaviour(new DamWaterManagement(20));
    }

    protected void takeDown() {

    }

    public class DamWaterManagement extends Behaviour {

        private int duration;
        private int counter;

        public DamWaterManagement(int duration)
        {
            this.duration = duration;
        }

        public void action() {

            System.out.println("Dam Management");
            counter++;
        }

        public boolean done() {

            if(counter == duration)
                return true;

            return false;
        }

    }

}
