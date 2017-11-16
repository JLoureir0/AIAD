package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class CityAgent extends Agent{

    protected void setup() {

        System.out.println("City Agent");
        addBehaviour(new CityWaterManagement(20));
    }

    protected void takeDown() {

    }

    public class CityWaterManagement extends Behaviour{

        private int duration;
        private int counter;

        public CityWaterManagement(int duration)
        {
            this.duration = duration;
        }

        public void action() {

            System.out.println("City Management");
            counter++;
        }

        public boolean done() {

            if(counter == duration)
                return true;

            return false;
        }

    }

}
