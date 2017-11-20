package agents;

import static model.Utils.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FarmAgent extends Agent {

    private static String farmNo;
    private static double ax, bx, cx;
    private static String state = "send";

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 4) {

            System.out.println("Incorrect arguments");
            takeDown();
            return;

        }

        farmNo = args[0].toString();

        ax = Double.parseDouble(args[1].toString());
        bx = Double.parseDouble(args[2].toString());
        cx = Double.parseDouble(args[3].toString());

        System.out.println("Farm" + farmNo + " Agent");
        addBehaviour(new FarmWaterManagement(20));

    }

    protected void takeDown() {
        doDelete();
    }

    private double objFunction(double x) {
        return ax*x*x+bx*x+cx;
    }

    public class FarmWaterManagement extends Behaviour {

        private int duration;
        private int counter;

        public FarmWaterManagement(int duration) {
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
