package model;

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


    public static void main(String[] args){
        Scenario s = new Scenario(1);
        System.out.println(s.getQ1());
        System.out.println(s.getQ2());
        System.out.println(s.getS());
    }

}
