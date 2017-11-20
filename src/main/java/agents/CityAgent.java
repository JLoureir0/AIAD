package agents;

import static model.Utils.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class CityAgent extends Agent {

    private static double a1, b1, c1;
    private static String state = "send";

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 3) {

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

    private double objFunction(double x1) {
        return a1*x1*x1+b1*x1+c1;
    }

    public class CityWaterManagement extends Behaviour {

        private int duration;
        private int counter;

        public CityWaterManagement(int duration) {
            this.duration = duration;
        }

        public void action() {

            if(state.equals("receive")) {

                ACLMessage msg = receive();

                if(msg != null) {

                    System.out.println(getLocalName() + " received ACLMessage" + ": " + msg.getContent());
                    state = "send";

                }
                else {

                    block();

                }

            }

            if(state.equals("send")) {

                for(String agent : agentsNames) {

                    if(!agent.equals(getLocalName())) {

                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        msg.addReceiver(new AID(agent, AID.ISLOCALNAME));
                        msg.setLanguage("English");
                        //msg.setOntology("Value sharing");
                        msg.setContent("Hi " + agent + ", from " + getLocalName());
                        send(msg);

                        System.out.println(getLocalName() + " sent ACLMessage" + ": " + msg.getContent());

                    }

                }

                state = "receive";

            }

            counter++;

        }

        public boolean done() {

            if(counter == duration)
                return true;

            return false;

        }

    }

}
