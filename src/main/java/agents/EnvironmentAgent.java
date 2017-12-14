package agents;

import model.Scenario;

import static model.Utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class EnvironmentAgent extends Agent{

    private ArrayList<Scenario> scenarios;

    protected void setup() {

        Object[] args = getArguments();

        if(args.length != 1) {

            System.out.println("Incorrect arguments for " + getLocalName());
            takeDown();
            return;

        }

        this.scenarios = (ArrayList<Scenario>) args[0];

        addBehaviour(new EnvironmentBehaviour());

    }

    protected void takeDown() {
        doDelete();
    }

    public class EnvironmentBehaviour extends CyclicBehaviour {

        public void action() {
            for (int i = 0; i < scenarios.size(); i++) {

                HashMap<String, Double> agentsActions = new HashMap<>();

                System.out.println("\n===Scenario " + scenarios.get(i) + "===" );

                while(agentsActions.keySet().size() != 4) {

                    ACLMessage incomingAction = blockingReceive();

                    try {
                        Double waterPercentage = (Double) incomingAction.getContentObject();
                        agentsActions.put(incomingAction.getSender().getLocalName(), waterPercentage);

                    }catch(UnreadableException e) {
                        e.printStackTrace();
                    }
                }

                if(!scenarios.get(i).isFinalScenario()) {

                    sendRewardAndNextScenario(scenarios.get(i), agentsActions, scenarios.get(i+1));
                }
                else {

                    sendRewardAndNextScenario(scenarios.get(i), agentsActions, scenarios.get(0));
                }
            }
        }

        private void sendRewardAndNextScenario(Scenario currentScenario, HashMap<String, Double> agentsActions, Scenario nextScenario) {
            
            Double reward = calculateReward(currentScenario, agentsActions);

            for(String agent : agentsNames) {
                
                if(agent != ENVIRONMENT_AGENT) {

                    ACLMessage rewardMessage = new ACLMessage(ACLMessage.INFORM);
                    rewardMessage.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    try {
                        rewardMessage.setContentObject(reward);
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                    send(rewardMessage);

                    ACLMessage scenarioMessage = new ACLMessage(ACLMessage.INFORM);
                    scenarioMessage.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    try {
                        scenarioMessage.setContentObject(nextScenario);
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                    send(scenarioMessage);
                }
            }
        }

        private Double calculateReward(Scenario currentScenario, HashMap<String, Double> agentsActions) {

            Double cityAgentWater = alpha1 + (agentsActions.get(CITY_AGENT)*(currentScenario.getQ1()-alpha2-alpha1)) / 100;
            Double damAgentWater = alpha2 + (agentsActions.get(DAM_AGENT)*(currentScenario.getQ1()-alpha1-alpha2)) / 100;
            Double farm1AgentWater = alpha4 + (agentsActions.get(FARM_AGENT_1)*(currentScenario.getQ2()-alpha3-alpha4)) / 100;
            Double farm2AgentWater = alpha6 + (agentsActions.get(FARM_AGENT_2)*(currentScenario.getQ1()+currentScenario.getQ2()-alpha1-alpha4-alpha3-alpha5-alpha6)) / 100;
/*
            Double cityAgentWater = alpha1 + (agentsActions.get(CITY_AGENT)*(currentScenario.getQ1()-alpha2-alpha1)) / 100;
            Double damAgentWater = (agentsActions.get(DAM_AGENT)*(currentScenario.getS()+currentScenario.getQ1()-alpha1)) / 100;
            Double farm1AgentWater = alpha3 + (agentsActions.get(FARM_AGENT_1)*(currentScenario.getQ2()-alpha4-alpha3)) / 100;
            Double farm2AgentWater = alpha5 + (agentsActions.get(FARM_AGENT_2)*(currentScenario.getS()+currentScenario.getQ1()+currentScenario.getQ2()-alpha1-alpha3-alpha6-alpha5)) / 100;
*/
            Double eco1Water = currentScenario.getQ2()-farm1AgentWater;
            Double eco2Water = damAgentWater+eco1Water-farm2AgentWater;

            System.out.println("City: " + cityAgentWater);
            System.out.println("Dam: " + damAgentWater);
            System.out.println("Eco1: " + eco1Water);
            System.out.println("Farm1: " + farm1AgentWater);
            System.out.println("Eco2: " + eco2Water);
            System.out.println("Farm2: " + farm2AgentWater);

            Double cityReward = a1*cityAgentWater*cityAgentWater+b1*cityAgentWater+c1;
            Double damReward = a2*damAgentWater*damAgentWater+b2*damAgentWater+c2;
            Double eco1Reward = a3*eco1Water*eco1Water+b3*eco1Water+c3;
            Double farm1Reward = a4*farm1AgentWater*farm1AgentWater+b4*farm1AgentWater+c4;
            Double eco2Reward = a5*eco2Water*eco2Water+b5*eco2Water+c5;
            Double farm2Reward = a6*farm2AgentWater*farm2AgentWater+b6*farm2AgentWater+c6;

            Double reward = cityReward+damReward+eco1Reward+eco2Reward+farm1Reward+farm2Reward;

            Double penality1 = 0.0;
            Double penality2 = 0.0;
            Double penality3 = 0.0;
            Double penality4 = 0.0;
            Double penality5 = 0.0;
            Double penality6 = 0.0;
            Double penality7 = 0.0;
            Double penality8 = 0.0;
            Double penality9 = 0.0;

            if(alpha1-cityAgentWater > 0) {
                penality1 = 100*(alpha1-cityAgentWater+1);
            }
            if(alpha2-currentScenario.getQ1()+cityAgentWater > 0) {
                penality2 = 100*(alpha2-currentScenario.getQ1()+cityAgentWater+1);
            }
            if(damAgentWater-currentScenario.getS()-currentScenario.getQ1()+cityAgentWater > 0) {
                penality3 = 100*(damAgentWater-currentScenario.getS()-currentScenario.getQ1()+cityAgentWater+1);
            }
            if(alpha4-eco1Water > 0) {
                penality4 = 100*(alpha4-eco1Water+1);
            }
            if(alpha3-farm1AgentWater > 0) {
                penality5 = 100*(alpha3-farm1AgentWater+1);
            }
            if(alpha4-currentScenario.getQ2()+farm1AgentWater > 0) {
                penality6 = 100*(alpha4-currentScenario.getQ2()+farm1AgentWater+1);
            }
            if(alpha6-eco2Water > 0) {
                penality7 = 100*(alpha6-eco2Water+1);
            }
            if(alpha5-farm2AgentWater > 0) {
                penality8 = 100*(alpha5-farm2AgentWater+1);
            }
            if(alpha6-damAgentWater-eco1Water+farm2AgentWater > 0){
                penality9 = 100*(alpha6-damAgentWater-eco1Water+farm2AgentWater+1);
            }

            Double penality = penality1+penality2+penality3+penality4+penality5+penality6+penality7+penality8+penality9;

            Double rewardWithPenality = reward-penality;

            System.out.println("\nReward:" + reward);

            return rewardWithPenality;
        }
    }
}