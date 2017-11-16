package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class CityAgent extends Agent{

    private static double a1, b1, c1;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 3){
            System.out.println("Incorrect arguments");
            doDelete();
            return;
        }

        a1 = Double.parseDouble(args[0].toString());
        b1 = Double.parseDouble(args[1].toString());
        c1 = Double.parseDouble(args[2].toString());

        System.out.println("City Agent");
        addBehaviour(new CityWaterManagement(20));
    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x1){
        return a1*x1*x1+b1*x1+c1;
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
