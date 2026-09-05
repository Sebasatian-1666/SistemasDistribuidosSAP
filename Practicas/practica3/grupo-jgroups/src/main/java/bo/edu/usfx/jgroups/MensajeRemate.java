package bo.edu.usfx.jgroups;

import java.io.Serializable;

public class MensajeRemate implements Serializable {
    private static final long serialVersionUID = 1L;
    private final TipoMensaje tipo;
    private final String articulo;
    private Subasta subasta;
    private Puja puja;
    private String ganador;

    // Constructor para Nueva Subasta
    public MensajeRemate(TipoMensaje tipo, String articulo, Subasta subasta) {
        this.tipo = tipo;
        this.articulo = articulo;
        this.subasta = subasta;
    }

    // Constructor para Puja Aceptada
    public MensajeRemate(TipoMensaje tipo, String articulo, Puja puja) {
        this.tipo = tipo;
        this.articulo = articulo;
        this.puja = puja;
    }

    // Constructor para Cierre de Subasta
    public MensajeRemate(TipoMensaje tipo, String articulo, String ganador) {
        this.tipo = tipo;
        this.articulo = articulo;
        this.ganador = ganador;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public String getArticulo() {
        return articulo;
    }

    public Subasta getSubasta() {
        return subasta;
    }

    public Puja getPuja() {
        return puja;
    }

    public String getGanador() {
        return ganador;
    }
}