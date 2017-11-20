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

    private static Profile p;
    private static ContainerController container;

    public Watershed() {

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

            container.createNewAgent(agentsNames[0], EnvironmentAgent.class.getName(), null);
            container.createNewAgent(agentsNames[1], CityAgent.class.getName(), new Object[] {a1, b1, c1});
            container.createNewAgent(agentsNames[2], DamAgent.class.getName(), new Object[] {a2, b2, c2});
            container.createNewAgent(agentsNames[3], FarmAgent.class.getName(), new Object[] {new String("1"), a4, b4, c4});
            container.createNewAgent(agentsNames[4], FarmAgent.class.getName(), new Object[] {new String("2"), a6, b6, c6});

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
