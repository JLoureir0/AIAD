package model;

import agents.*;
import static model.Utils.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Watershed {

    //Defining constants
    private static final double a1 = -0.2;
    private static final double a2 = -0.06;
    private static final double a3 = -0.29;
    private static final double a4 = -0.13;
    private static final double a5 = -0.056;
    private static final double a6 = -0.15;

    private static final double b1 = 6;
    private static final double b2 = 2.5;
    private static final double b3 = 6.28;
    private static final double b4 = 6;
    private static final double b5 = 3.74;
    private static final double b6 = 7.6;

    private static final int c1 = -5;
    private static final int c2 = 0;
    private static final int c3 = -3;
    private static final int c4 = -6;
    private static final int c5 = -23;
    private static final int c6 = -15;

    private static final int alpha1 = 12;
    private static final int alpha2 = 10;
    private static final int alpha3 = 8;
    private static final int alpha4 = 6;
    private static final int alpha5 = 15;
    private static final int alpha6 = 10;

    private static Profile p;
    private static ContainerController container;

    private int nEpisodes, learningRate, discountFactor;
    private Scenario scenario;

    public Watershed(int scenarioNo, int nEpisodes, int learningRate, int discountFactor) {

        this.nEpisodes = nEpisodes;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;

        scenario = new Scenario(scenarioNo);

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

            container.createNewAgent(agentsNames[0], EnvironmentAgent.class.getName(), new Object[] {scenario, nEpisodes});
            container.createNewAgent(agentsNames[1], CityAgent.class.getName(), new Object[] {scenario,nEpisodes, a1, b1, b1});
            container.createNewAgent(agentsNames[2], DamAgent.class.getName(), new Object[] {scenario,nEpisodes,a1,b1,b1});
            container.createNewAgent(agentsNames[3], FarmAgent.class.getName(), new Object[] {scenario, nEpisodes, a4, b4, b4});
            container.createNewAgent(agentsNames[4], FarmAgent.class.getName(), new Object[] {scenario, nEpisodes, a1,b1,b1});

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
