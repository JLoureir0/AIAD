package agents;

import static model.Utils.*;
import model.Scenario;
import behaviours.QLearning;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FarmAgent extends Agent {

    private static int farmNo;
    private static double ax, bx, cx;
    private static int alphax;
    private static Scenario scenario;
    private static int nEpisodes;
    private static String state = SEND;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 7) {

            System.out.println("Incorrect arguments for " + getLocalName());
            takeDown();
            return;

        }

        farmNo = Integer.parseInt(args[0].toString());

        ax = Double.parseDouble(args[1].toString());
        bx = Double.parseDouble(args[2].toString());
        cx = Double.parseDouble(args[3].toString());
        alphax = Integer.parseInt(args[4].toString());
        scenario = (Scenario) args[5];
        nEpisodes = Integer.parseInt(args[6].toString());

        System.out.println(getLocalName());
        addBehaviour(new FarmWaterManagement());

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x) {
        return ax*x*x+bx*x+cx;
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
