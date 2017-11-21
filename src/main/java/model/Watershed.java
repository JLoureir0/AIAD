package model;

import agents.*;
import static model.Utils.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class Watershed {

    //Defining constants
    private static final double a1 = -0.2;
    private static final double a2 = -0.06;
    private static final double a3 = -0.29;
    private static final double a4 = -0.13;
    private static final double a5 = -0.056;
    private static final double a6 = -0.15;

    private static final int b1 = 6;
    private static final double b2 = 2.5;
    private static final double b3 = 6.28;
    private static final int b4 = 6;
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

    private static Scenario scenario;

    private static int learningRate, discountFactor, nEpisodes;

    private static Profile p;
    private static ContainerController container;

    public Watershed(int scenarioNo, int nEpisodes, int learningRate, int discountFactor) {

        this.nEpisodes = nEpisodes;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;

        initScenario(scenarioNo);

        if(!initAgents())
            return;

        if(!startAgents())
            return;

    }

    private void initScenario(int scenarioNo) {

        scenario = new Scenario(scenarioNo);

    }

    private boolean initAgents() {

        // JADE Main Container
        p = new ProfileImpl(true);
        container = jade.core.Runtime.instance().createMainContainer(p);

        // Add agents to container
        try {

            container.createNewAgent(agentsNames[0], EnvironmentAgent.class.getName(), new Object[] {scenario, nEpisodes});
            container.createNewAgent(agentsNames[1], CityAgent.class.getName(), new Object[] {a1, b1, c1, alpha1, scenario, nEpisodes});
            container.createNewAgent(agentsNames[2], DamAgent.class.getName(), new Object[] {a2, b2, c2, alpha2, scenario, nEpisodes});
            container.createNewAgent(agentsNames[3], FarmAgent.class.getName(), new Object[] {farmNo1, a4, b4, c4, alpha4, scenario, nEpisodes});
            container.createNewAgent(agentsNames[4], FarmAgent.class.getName(), new Object[] {farmNo2, a6, b6, c6, alpha6, scenario, nEpisodes});

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
