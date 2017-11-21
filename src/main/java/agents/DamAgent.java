package agents;

import static model.Utils.*;
import behaviours.QLearning;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class DamAgent extends Agent {

    private static double a2, b2, c2;
    private static String state = SEND;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 3) {

            System.out.println("Incorrect arguments");
            doDelete();
            return;

        }

        a2 = Double.parseDouble(args[0].toString());
        b2 = Double.parseDouble(args[1].toString());
        c2 = Double.parseDouble(args[2].toString());

        System.out.println(getLocalName());
        addBehaviour(new DamWaterManagement());

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x2) {
        return a2*x2*x2+b2*x2+c2;
    }

    public class DamWaterManagement extends Behaviour {

        private int counter = 0;
        private QLearning qLearning;

        public void action() {

            if(state.equals(RECEIVE)) {

                ACLMessage msg = receive();

                if(msg != null) {

                    System.out.println(getLocalName() + " received ACLMessage" + ": " + msg.getContent());
                    state = SEND;

                }
                else {

                    block();

                }

            }

            if(state.equals(SEND)) {

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

                state = RECEIVE;

            }

            counter++;

        }

        public boolean done() {

            return (counter == nEpisodes);

        }

    }

}
