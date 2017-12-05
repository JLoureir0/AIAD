package agents;

import static model.Utils.*;
import model.Scenario;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SuperAgent extends Agent {

    private Scenario scenario;
    private int numberOfEpisodes;

    //QLearning
    private double epsilon, discountFactor, learningRate;

    protected void setup() {

        Object[] args = getArguments();

        if (args.length != 5) {

            System.out.println("Incorrect arguments for " + getLocalName());
            takeDown();
            return;

        }

        scenario = (Scenario) args[0];
        numberOfEpisodes = (int) args[1];

        epsilon = (double) args[2];
        discountFactor = (double) args[3];
        learningRate = (double) args[4];

        System.out.println(getLocalName());
        addBehaviour(new QLearning());

    }

    protected void takeDown() {
        doDelete();
    }

    public class QLearning extends Behaviour {

        private int counter = 0;

        public void action() {
            sendToEnvironment();
            receiveFromEnvironment();

            counter++;
        }

        public boolean done() {
            return (counter == numberOfEpisodes);
        }

        private void sendToEnvironment() {
                
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID(ENVIRONMENT_AGENT, AID.ISLOCALNAME));
            msg.setLanguage("English");
            //msg.setOntology("Value sharing");
            msg.setContent("Hi " + ENVIRONMENT_AGENT + ", from " + getLocalName());
            send(msg);

            System.out.println(getLocalName() + " sent ACLMessage" + ": " + msg.getContent());
        }

        private void receiveFromEnvironment() {
            ACLMessage msg = receive();

            if(msg != null) {
                System.out.println(getLocalName() + " received ACLMessage" + ": " + msg.getContent());
            }
            else {
                block();
            }
        }
    }
}