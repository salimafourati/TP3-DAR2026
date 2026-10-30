package SharedPackage;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private double operande1;
    private String operateur;
    private double operande2;
    private boolean quitter;

    // Constructeur pour une opération normale
    public Operation(double operande1, String operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
        this.quitter = false;
    }

    // Constructeur pour quitter
    public Operation(boolean quitter) {
        this.quitter = quitter;
    }

    public double getOperande1() {
        return operande1;
    }

    public String getOperateur() {
        return operateur;
    }

    public double getOperande2() {
        return operande2;
    }

    public boolean isQuitter() {
        return quitter;
    }
}
