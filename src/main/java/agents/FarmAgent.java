package agents;

import static model.Utils.*;
import model.Scenario;
import behaviours.QLearning;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FarmAgent extends Agent {

    private double a, b, c;
    private int alpha;

    private Scenario scenario;
    private int nEpisodes;

    private static String state = SEND;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 6) {

            System.out.println("Incorrect arguments for " + getLocalName());
            takeDown();
            return;

        }

        a = (double) args[0];
        b = (double) args[1];
        c = (int) args[2];
        alpha = (int) args[3];

        scenario = (Scenario) args[4];
        nEpisodes = (int) args[5];

        System.out.println(getLocalName());
        addBehaviour(new FarmWaterManagement());

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x) {
        return a*x*x+b*x+c;
    }

    public class FarmWaterManagement extends Behaviour {

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
