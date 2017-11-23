package agents;

import static model.Utils.*;
import model.Scenario;
import behaviours.QLearning;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class CityAgent extends Agent {

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

        a = Double.parseDouble(args[0].toString());
        b = Double.parseDouble(args[1].toString());
        c = Double.parseDouble(args[2].toString());
        alpha = Integer.parseInt(args[3].toString());

        scenario = (Scenario) args[4];
        nEpisodes = Integer.parseInt(args[5].toString());

        System.out.println(getLocalName());
        addBehaviour(new CityWaterManagement());

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x) {
        return a*x*x+b*x+c;
    }

    public class CityWaterManagement extends Behaviour {

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
