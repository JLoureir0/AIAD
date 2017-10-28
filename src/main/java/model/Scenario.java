package model;

//Watershed Flow Conditions
public class Scenario {

    private int Q1;
    private int Q2;
    private int S;

    public Scenario(int n){
        if(n < 1 || n > 3){
            System.out.println("Invalid Scenario");
            return;
        }

        if(n==1){
            Q1 = 160;
            Q2 = 65;
            S = 15;
        }
        else if(n==2){
            Q1 = 115;
            Q2 = 50;
            S = 12;
        }
        else{
            Q1 = 80;
            Q2 = 35;
            S = 10;
        }
    }

    public int getQ1(){
        return Q1;
    }

    public int getQ2(){
        return Q2;
    }

    public int getS(){
        return S;
    }
}
