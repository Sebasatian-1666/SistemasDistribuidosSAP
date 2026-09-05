package bo.edu.usfx.jgroups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subasta implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String articulo;
    private final double precioBase;
    private final long tiempoCierreAbsoluto;
    private boolean abierta;
    private final List<Puja> historialPujas = new ArrayList<>();

    public Subasta(String articulo, double precioBase, long tiempoCierreAbsoluto) {
        this.articulo = articulo;
        this.precioBase = precioBase;
        this.tiempoCierreAbsoluto = tiempoCierreAbsoluto;
        this.abierta = true;
    }

    public String getArticulo() {
        return articulo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public long getTiempoCierreAbsoluto() {
        return tiempoCierreAbsoluto;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void cerrar() {
        this.abierta = false;
    }

    public void agregarPuja(Puja puja) {
        historialPujas.add(puja);
    }

    public double getMejorMontoActual() {
        if (historialPujas.isEmpty()) {
            return precioBase;
        }
        return historialPujas.get(historialPujas.size() - 1).getMonto();
    }

    public String getMejorPostor() {
        if (historialPujas.isEmpty()) {
            return "Ninguno";
        }
        return historialPujas.get(historialPujas.size() - 1).getPostor();
    }

    public List<Puja> getHistorialPujas() {
        return historialPujas;
    }
}