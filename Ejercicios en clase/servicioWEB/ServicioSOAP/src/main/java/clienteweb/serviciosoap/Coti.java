package clienteweb.serviciosoap;

public class Coti {
    public String fec;
    public double val;
    public double ofi;

    public Coti() {} // SOAP exige esto vacío

    public Coti(String fec, double val, double ofi) {
        this.fec = fec;
        this.val = val;
        this.ofi = ofi;
    }
}