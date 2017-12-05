package behaviours;

import java.util.*;

import jade.core.behaviours.Behaviour;

public class QLearning {

    private HashSet<String> actionsSet;
    private HashSet<String> statesSet;

    private double[][] qTable;

    private double learningRate;
    private double rewardDecay;
    private double epsilonGreedy;

    public QLearning(HashSet<String> statesSet, HashSet<String> actionsSet, double learningRate, double rewardDecay, double epsilonGreedy) {

        // Possible states and actions
        this.statesSet = statesSet;
        this.actionsSet = actionsSet;

        // Hyperparameters for the Q Learning algorithm
        this.learningRate = learningRate;
        this.rewardDecay = rewardDecay;
        this.epsilonGreedy = epsilonGreedy;

        // The Q Learning table
        this.qTable = new double[statesSet.size()][actionsSet.size()];

    }

    public void updateQTable(int state, int action, double reward) {

        // Q(state,action)= Q(state,action) + learningRate * (R(state,action) + rewardDecay * Max(next state, all actions) - Q(state,action))
        double maxQValue = this.qTable[state][getBestAction(state)];

        this.qTable[state][action] += this.learningRate*(reward + this.rewardDecay * maxQValue - this.qTable[state][action]);

    }

    public int getAction(int state) {

        int nextAction;
        Random random = new Random();

        // Applying ε-greedy strategy to choose next action
        if(random.nextDouble() > this.epsilonGreedy)
            // Choose best action
            nextAction = getBestAction(state);
        else
            // Choose random action
            nextAction = random.nextInt(this.actionsSet.size());

        // Update epsilon
        this.epsilonGreedy = 0.99*epsilonGreedy;

        return nextAction;

    }

    private int getBestAction(int state) {

        // Best action is the one with higher Q value
        double maxQValue = 0;

        // It's possible that more than one action have the same Q value
        ArrayList<Integer> bestActions = new ArrayList();

        // Find the maximum Q value
        for(int i = 0; i < this.qTable[state].length; i++) {
            if(this.qTable[state][i] > maxQValue)
                maxQValue = this.qTable[state][i];
        }

        // Find the actions with the maximum Q value
        for(int i = 0; i < this.qTable[state].length; i++) {
            if(this.qTable[state][i] == maxQValue)
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
}
