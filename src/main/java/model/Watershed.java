package model;

import agents.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class Watershed {

    //Defining constants
    public static final double a1 = -0.2;
    public static final double a2 = -0.06;
    public static final double a3 = -0.29;
    public static final double a4 = -0.13;
    public static final double a5 = -0.056;
    public static final double a6 = -0.15;

    public static final int b1 = 6;
    public static final double b2 = 2.5;
    public static final double b3 = 6.28;
    public static final int b4 = 6;
    public static final double b5 = 3.74;
    public static final double b6 = 7.6;

    public static final int c1 = -5;
    public static final int c2 = 0;
    public static final int c3 = -3;
    public static final int c4 = -6;
    public static final int c5 = -23;
    public static final int c6 = -15;

    public static final int alpha1 = 12;
    public static final int alpha2 = 10;
    public static final int alpha3 = 8;
    public static final int alpha4 = 6;
    public static final int alpha5 = 15;
    public static final int alpha6 = 10;

    private static ArrayList<AgentController> agents;

    public Watershed(){

        if(!initAgents())
            return;

        if(!startAgents())
            return;
    }

    private boolean initAgents(){

        // Start the JADE Main Container
        Profile p = new ProfileImpl(true);
        ContainerController cc = jade.core.Runtime.instance().createMainContainer(p);

        agents = new ArrayList<>();

        // To start agents on the created (main) container
        try {

            AgentController environmentController = cc.createNewAgent("EnvironmentAgent", EnvironmentAgent.class.getName(), null);
            AgentController cityController = cc.createNewAgent("CityAgent", CityAgent.class.getName(), null);
            AgentController damController = cc.createNewAgent("DamAgent", DamAgent.class.getName(), null);
            AgentController farm1Controller = cc.createNewAgent("FarmAgent1", FarmAgent.class.getName(), new Object[] {new String("1")});
            AgentController farm2Controller = cc.createNewAgent("FarmAgent2", FarmAgent.class.getName(), new Object[] {new String("2")});


            agents.add(environmentController);
            agents.add(cityController);
            agents.add(damController);
            agents.add(farm1Controller);
            agents.add(farm2Controller);

        }
        catch(StaleProxyException e) {

            System.out.println("Error inserting agents in the controller");
            e.printStackTrace();
            return false;

        }

        return true;
    }

    private boolean startAgents(){

       int nAgents = agents.size();

        try {

            for(int i=0; i<nAgents; i++)
                agents.get(i).start();

        }
        catch(StaleProxyException e) {

            System.out.println("Error inserting agents in the controller");
            e.printStackTrace();
            return false;

        }

        return true;
    }

}
