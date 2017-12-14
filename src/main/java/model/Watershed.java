package model;

import agents.*;
import static model.Utils.*;

import java.util.ArrayList;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Watershed {


    private static Profile p;
    private static ContainerController container;

    private int nEpisodes;
    private double learningRate, discountFactor;
    private ArrayList<Scenario> scenarios;

    public Watershed(int scenarioNo, int nEpisodes, double learningRate, double discountFactor) {

        this.nEpisodes = nEpisodes;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;

        scenarios = new ArrayList<>();
        Scenario scenario1 = new Scenario(160, 65, 15);
        Scenario scenario2 = new Scenario(115, 50, 12);
        Scenario scenario3 = new Scenario(80, 35, 10);
        scenario3.setFinalScenario(true);

        scenarios.add(scenario1);
        scenarios.add(scenario2);
        scenarios.add(scenario3);

        if(!initAgents())
            return;

        if(!startAgents())
            return;

    }

    private boolean initAgents() {

        // JADE Main Container
        p = new ProfileImpl(true);
        container = jade.core.Runtime.instance().createMainContainer(p);

        // Add agents to container
        try {

            container.createNewAgent(agentsNames[0], EnvironmentAgent.class.getName(), new Object[] {scenarios});
            container.createNewAgent(agentsNames[1], CityAgent.class.getName(), new Object[] {scenarios.get(0), 0.01, nEpisodes, 0.01, this.discountFactor, this.learningRate});
            container.createNewAgent(agentsNames[2], DamAgent.class.getName(), new Object[] {scenarios.get(0), 0.01, nEpisodes, 0.01, this.discountFactor, this.learningRate});
            container.createNewAgent(agentsNames[3], FarmAgent.class.getName(), new Object[] {scenarios.get(0), 0.01, nEpisodes, 0.01, this.discountFactor, this.learningRate});
            container.createNewAgent(agentsNames[4], FarmAgent.class.getName(), new Object[] {scenarios.get(0), 0.01, nEpisodes, 0.01, this.discountFactor, this.learningRate});

        }
        catch(StaleProxyException e) {

            System.out.println("Error inserting agents in the controller");
            e.printStackTrace();
            return false;

        }

        return true;

    }

    private boolean startAgents() {

       try {

            for(String name : agentsNames)
                container.getAgent(name).start();

       }
       catch(ControllerException e) {

            System.out.println("Error starting agents");
            e.printStackTrace();
            return false;

       }

       return true;

    }

}
