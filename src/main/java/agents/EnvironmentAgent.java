package agents;

import static model.Utils.*;
import model.Scenario;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnvironmentAgent extends Agent{

    private Scenario scenario;
    private int nEpisodes;

    private static String state = SEND;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 2) {

            System.out.println("Incorrect arguments for " + getLocalName());
            takeDown();
            return;

        }

        scenario = (Scenario) args[0];
        nEpisodes = Integer.parseInt(args[1].toString());

        System.out.println(getLocalName() + " nEpisodes " + nEpisodes + " scenario " + scenario.getQ1());
        addBehaviour(new EnvironmentBehaviour());

    }

    protected void takeDown() {
        doDelete();
    }

    public class EnvironmentBehaviour extends Behaviour {

        private int counter = 0;

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
