package agents;

import static model.Utils.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnvironmentAgent extends Agent{

    private static String state = "send";

    protected void setup() {

        System.out.println("Environment Agent");
        addBehaviour(new EnvironmentBehaviour(20));

    }

    protected void takeDown() {
        doDelete();
    }

    public class EnvironmentBehaviour extends Behaviour {

        private int duration;
        private int counter;

        public EnvironmentBehaviour(int duration) {
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
