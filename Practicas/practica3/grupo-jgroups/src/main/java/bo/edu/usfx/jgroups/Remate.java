package bo.edu.usfx.jgroups;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.jgroups.*;
import org.jgroups.util.Util;

public class Remate implements Receiver {
    private JChannel canal;
    private final String nombreParticipante;
    private View vistaAnterior;

    private final Map<String, Subasta> subastas = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public Remate(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }

    @Override
    public void viewAccepted(View vista) {
        if (vistaAnterior != null) {
            Address[][] cambios = View.diff(vistaAnterior, vista);
            for (Address a : cambios[0]) System.out.println("** SE UNIO: " + a);
            for (Address a : cambios[1]) System.out.println("** SALIO: " + a);
        }
        vistaAnterior = vista;
        
        System.out.println("** Vista " + vista.getViewId().getId() 
            + " | Coordinador actual: " + vista.getCoord() 
            + " | Miembros: " + vista.getMembers());

       if (vista.getCoord() != null && vista.getCoord().equals(canal.getAddress())) {
        reprogramarTemporizadoresPendientes();
    }
    }

    @Override
    public void receive(Message msg) {
        MensajeRemate m = (MensajeRemate) msg.getObject();
        
        synchronized (subastas) {
            switch (m.getTipo()) {
                case INTENTO_PUJA:
                    // REQUISITO 5: Solo el coordinador valida las propuestas
                    if (canal.getAddress().equals(canal.getView().getCoord())) {
                        Subasta sEvaluar = subastas.get(m.getArticulo());
                        if (sEvaluar != null && sEvaluar.isAbierta() && m.getPuja().getMonto() > sEvaluar.getMejorMontoActual()) {
                            // La puja es válida, el coordinador autoriza y difunde a TODOS
                            MensajeRemate aceptada = new MensajeRemate(TipoMensaje.PUJA_ACEPTADA, m.getArticulo(), m.getPuja());
                            try {
                                canal.send(new ObjectMessage(null, aceptada));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case NUEVA_SUBASTA:
                    subastas.put(m.getArticulo(), m.getSubasta());
                    System.out.println("\n[NUEVA SUBASTA] Artículo: " + m.getArticulo() 
                        + " | Base: " + m.getSubasta().getPrecioBase());
                    if (canal.getAddress().equals(canal.getView().getCoord())) {
                        reprogramarTemporizadoresPendientes();
                    }
                    break;
                case PUJA_ACEPTADA:
                    Subasta sub = subastas.get(m.getArticulo());
                    if (sub != null && sub.isAbierta()) {
                        sub.agregarPuja(m.getPuja());
                        System.out.println("\n[PUJA ACEPTADA] " + m.getPuja().getPostor() 
                            + " pujó " + m.getPuja().getMonto() + " por " + m.getArticulo());
                    }
                    break;
                case CIERRE_SUBASTA:
                    Subasta subCerrada = subastas.get(m.getArticulo());
                    if (subCerrada != null) {
                        subCerrada.cerrar();
                        System.out.println("\n[SUBASTA CERRADA] Artículo: " + m.getArticulo() 
                            + " | Ganador: " + (m.getGanador() != null ? m.getGanador() : "Desierto"));
                    }
                    break;
            }
        }
    }

    public void getState(OutputStream salida) throws Exception {
        synchronized (subastas) {
            Util.objectToStream(subastas, new DataOutputStream(salida));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream entrada) throws Exception {
        Map<String, Subasta> estadoRemoto = (Map<String, Subasta>) Util.objectFromStream(new DataInputStream(entrada));
        synchronized (subastas) {
            subastas.clear();
            subastas.putAll(estadoRemoto);
            System.out.println("** Estado sincronizado correctamente. Subastas activas: " + subastas.size());
        }
    }

    public void iniciar() throws Exception {
        canal = new JChannel(System.getProperty("config", "udp.xml"));
        canal.name(nombreParticipante);
        canal.setReceiver(this);
        canal.connect("RemateSIS258");
        
        canal.getState(null, 10000);

        leerTeclado();
        canal.close();
        scheduler.shutdown();
    }

    private void leerTeclado() throws Exception {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=== SISTEMA DE SUBASTA REMATE-USFX ===");
        System.out.println("Comandos: /crear <articulo> <precio> <segundos> | /subastas | /pujar <articulo> <monto> | /estado <articulo> | /quien | /salir");
        
        String linea;
        while ((linea = teclado.readLine()) != null) {
            if (linea.equals("/salir")) break;
            
            if (linea.startsWith("/crear ")) {
                procesarCreacion(linea);
            } else if (linea.equals("/subastas")) {
                listarSubastas();
            } else if (linea.startsWith("/pujar ")) {
                procesarPuja(linea);
            } else if (linea.startsWith("/estado ")) {
                verEstadoArticulo(linea);
            } else if (linea.equals("/quien")) {
                System.out.println("Participantes: " + canal.getView().getMembers() 
                    + " | Coordinador: " + canal.getView().getCoord());
            }
        }
    }

    private void procesarCreacion(String linea) throws Exception {
        String[] partes = linea.split(" ");
        if (partes.length < 4) {
            System.out.println("Uso: /crear <articulo> <precio_base> <segundos>");
            return;
        }
        String articulo = partes[1];
        double precioBase = Double.parseDouble(partes[2]);
        long segundos = Long.parseLong(partes[3]);

        long tiempoCierreAbsoluto = System.currentTimeMillis() + (segundos * 1000);
        Subasta nuevaSub = new Subasta(articulo, precioBase, tiempoCierreAbsoluto);

        MensajeRemate msg = new MensajeRemate(TipoMensaje.NUEVA_SUBASTA, articulo, nuevaSub);
        canal.send(new ObjectMessage(null, msg));
    }

    private void procesarPuja(String linea) throws Exception {
        String[] partes = linea.split(" ");
        if (partes.length < 3) {
            System.out.println("Uso: /pujar <articulo> <monto>");
            return;
        }
        String articulo = partes[1];
        double monto = Double.parseDouble(partes[2]);

        Subasta sub = subastas.get(articulo);
        if (sub == null || !sub.isAbierta()) {
            System.out.println("Error: La subasta no existe o ya cerró.");
            return;
        }
        if (monto <= sub.getMejorMontoActual()) {
            System.out.println("Error: El monto debe superar la puja actual (" + sub.getMejorMontoActual() + ")");
            return;
        }

        Puja puja = new Puja(nombreParticipante, monto);
        MensajeRemate msg = new MensajeRemate(TipoMensaje.INTENTO_PUJA, articulo, puja);
        
        // UNICAST: Enviar solo al coordinador actual para validación
        Address coordinador = canal.getView().getCoord();
        canal.send(new ObjectMessage(coordinador, msg));
        System.out.println("Propuesta enviada al coordinador en privado...");
    }

    private void listarSubastas() {
        synchronized (subastas) {
            System.out.println("--- SUBASTAS ACTIVAS ---");
            for (Subasta s : subastas.values()) {
                if (s.isAbierta()) {
                    long segundosRestantes = Math.max(0, (s.getTiempoCierreAbsoluto() - System.currentTimeMillis()) / 1000);
                    System.out.println("- Artículo: " + s.getArticulo() 
                        + " | Mejor Puja: " + s.getMejorMontoActual() 
                        + " (" + s.getMejorPostor() + ")" 
                        + " | Tiempo restante: " + segundosRestantes + "s");
                }
            }
        }
    }

    private void verEstadoArticulo(String linea) {
        String[] partes = linea.split(" ");
        if (partes.length < 2) return;
        Subasta s = subastas.get(partes[1]);
        if (s != null) {
            System.out.println("Historial de pujas para " + partes[1] + ":");
            for (Puja p : s.getHistorialPujas()) {
                System.out.println("  -> " + p.getPostor() + " ofertó " + p.getMonto());
            }
        } else {
            System.out.println("Artículo no encontrado.");
        }
    }

    private void reprogramarTemporizadoresPendientes() {
        for (Subasta s : subastas.values()) {
            if (s.isAbierta()) {
                long delay = s.getTiempoCierreAbsoluto() - System.currentTimeMillis();
                if (delay < 0) delay = 0;
                
                scheduler.schedule(() -> {
                    synchronized (subastas) {
                        if (s.isAbierta()) {
                            s.cerrar();
                            MensajeRemate msgCierre = new MensajeRemate(TipoMensaje.CIERRE_SUBASTA, s.getArticulo(), s.getMejorPostor());
                            try {
                                canal.send(new ObjectMessage(null, msgCierre));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String nombre = args.length > 0 ? args[0] : "Participante";
        new Remate(nombre).iniciar();
    }
}