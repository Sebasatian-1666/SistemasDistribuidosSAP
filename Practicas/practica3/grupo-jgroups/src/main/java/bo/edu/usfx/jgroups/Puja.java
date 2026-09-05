package bo.edu.usfx.jgroups;

import java.io.Serializable;

public class Puja implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String postor;
    private final double monto;

    public Puja(String postor, double monto) {
        this.postor = postor;
        this.monto = monto;
    }

    public String getPostor() {
        return postor;
    }

    public double getMonto() {
        return monto;
    }
}