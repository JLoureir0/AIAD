package agents;

import static model.Utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.Scenario;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class SuperAgent extends Agent {

    protected void setup() {

        System.out.println(getLocalName());
        addBehaviour(new QLearning());
    }

    protected void takeDown() {
        doDelete();
    }

    public class QLearning extends Behaviour {

        // States
        private Scenario initialScenario;
        private Scenario currentScenario;

        // Actions
        private int waterDiscretization; // Increment in percentage of the water that can be withdrawn

        // Number of episodes to run
        private int numberOfEpisodes;
        private int episodeCounter = 0;

        // Hyperparameters for the Q Learning algorithm
        private double epsilon, discountFactor, learningRate;

        // The Q Learning table
        private HashMap<Scenario, double[]> qTable;

        public QLearning() {

            Object[] args = getArguments();

            if (args.length != 6) {

                System.out.println("Incorrect arguments for " + getLocalName());
                takeDown();
                return;
            }

            this.initialScenario = (Scenario) args[0];
            this.currentScenario = (Scenario) args[0];

            this.waterDiscretization = (int) (100 / (double) args[1] + 1);

            this.numberOfEpisodes = (int) args[2];

            this.epsilon = (double) args[3];
            this.discountFactor = (double) args[4];
            this.learningRate = (double) args[5];

            this.qTable = new HashMap<>();
        }

        public void action() {

            do {

                if(!this.qTable.containsKey(this.currentScenario)) {

                    this.qTable.put(this.currentScenario, new double[this.waterDiscretization]);
                }

                int nextAction = chooseNextAction();
                double reward = takeAction(nextAction);
                Scenario nextScenario = getNextScenario();

                if(!this.qTable.containsKey(nextScenario)) {

                    this.qTable.put(nextScenario, new double[this.waterDiscretization]);
                }

                updateQTable(nextAction, reward, nextScenario);

                this.currentScenario = nextScenario;

            } while(!this.currentScenario.isFinalScenario());

            // Update epsilon
            this.epsilon = 0.99*this.epsilon;

            this.episodeCounter++;
        }

        public boolean done() {
            return (this.episodeCounter == this.numberOfEpisodes);
        }

        private int chooseNextAction() {

            int nextAction;
            Random random = new Random();

            // Applying ε-greedy strategy to choose next action
            if(random.nextDouble() > this.epsilon)
                // Choose best action
                nextAction = getBestAction(this.currentScenario);
            else
                // Choose random action
                nextAction = random.nextInt(this.waterDiscretization+1);

            return nextAction;
        }

        private int getBestAction(Scenario scenario) {

            // Best action is the one with higher Q value
            double maxQValue = 0;

            // It's possible that more than one action have the same Q value
            ArrayList<Integer> bestActions = new ArrayList<>();

            // Find the maximum Q value
            for(int i = 0; i < this.qTable.get(scenario).length; i++) {
                if(this.qTable.get(scenario)[i] > maxQValue)
                    maxQValue = this.qTable.get(scenario)[i];
            }

            // Find the actions with the maximum Q value
            for(int i = 0; i < this.qTable.get(scenario).length; i++) {
                if(this.qTable.get(scenario)[i] == maxQValue)
                    bestActions.add(i);
            }

            // If there is more than one, choose one at random
            if(bestActions.size() > 1) {
                Random random = new Random();
                return bestActions.get(random.nextInt(bestActions.size()));
            }

            // Otherwise return the one
            return bestActions.get(0);
        }

        private Double takeAction(int nextAction) {
            ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
            requestMessage.addReceiver(new AID(ENVIRONMENT_AGENT, AID.ISLOCALNAME));
            Double waterPercentage = (nextAction*100.0) / this.waterDiscretization;
            try {
                requestMessage.setContentObject(waterPercentage);
            }catch(IOException e) {
                e.printStackTrace();
            }
            send(requestMessage);

            ACLMessage responseMessage = blockingReceive();

            if(responseMessage != null) {
                try {
					return (Double) responseMessage.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
            }

            return null;
        }

        private Scenario getNextScenario() {

            ACLMessage nextScenarioMessage = blockingReceive();

            if(nextScenarioMessage != null) {
                try {
					return (Scenario) nextScenarioMessage.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
            }

            return null;
        }

        private void updateQTable(int action, double reward, Scenario nextScenario) {

            // Q(state,action)= Q(state,action) + learningRate * (R(state,action) + rewardDecay * Max(next state, all actions) - Q(state,action))
            
            double qPredicted = this.qTable.get(this.currentScenario)[action];
            double qTarget;

            if(this.currentScenario.isFinalScenario()) {
                qTarget = reward;
            }
            else {
                qTarget = reward + this.discountFactor * this.qTable.get(nextScenario)[getBestAction(nextScenario)];
            }

            this.qTable.get(this.currentScenario)[action] = qPredicted + this.learningRate*(qTarget - qPredicted);
        }
    }
}