package model;

import static model.Utils.*;

import java.io.Serializable;
import java.util.Objects;

public class Scenario implements Serializable {

    private int Q1;
    private int Q2;
    private int S;

    private boolean finalScenario = false;

    public Scenario(int n) {

        if(n < SCENARIO_0 || n > SCENARIO_2) {

            System.out.println("Invalid Scenario");
            return;

        }

        if(n == SCENARIO_0) {

            Q1 = 160;
            Q2 = 65;
            S = 15;

        }
        else if(n == SCENARIO_1) {

            Q1 = 115;
            Q2 = 50;
            S = 12;

        }
        else {

            Q1 = 80;
            Q2 = 35;
            S = 10;

        }

    }

    public Scenario(int Q1, int Q2, int S) {
        this.Q1 = Q1;
        this.Q2 = Q2;
        this.S = S;
    }

    public int getQ1() {
        return Q1;
    }

    public int getQ2() {
        return Q2;
    }

    public int getS() {
        return S;
    }

    public boolean isFinalScenario() {
        return finalScenario;
    }

    public void setFinalScenario(boolean finalScenario) {
        this.finalScenario = finalScenario;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(!(other instanceof Scenario)) return false;
                
        Scenario otherScenario = (Scenario) other;

        return this.Q1 == otherScenario.Q1 && this.Q2 == otherScenario.Q2 && this.S == otherScenario.S;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.Q1, this.Q2, this.S, this.finalScenario);
    }
}