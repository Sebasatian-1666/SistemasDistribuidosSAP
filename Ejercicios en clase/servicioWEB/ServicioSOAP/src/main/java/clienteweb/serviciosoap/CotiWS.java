package clienteweb.serviciosoap;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "CotiWS")
public class CotiWS {
    
    private static List<Coti> bd = new ArrayList<>();
    
    static {
        bd.add(new Coti("2025-04-04", 7.02, 6.97));
        bd.add(new Coti("2025-04-05", 7.05, 6.97));
    }

    @WebMethod(operationName = "obtenerCoti")
    public Coti obtenerCoti(@WebParam(name = "fec") String fec) {
        for (Coti c : bd) {
            if (c.fec.equals(fec)) return c;
        }
        return null;
    }

    @WebMethod(operationName = "registrarCoti")
    public Coti registrarCoti(@WebParam(name = "fec") String fec, @WebParam(name = "val") double val) throws Exception {
        for (Coti c : bd) {
            if (c.fec.equals(fec)) throw new Exception("Error: Fecha duplicada");
        }
        if (val <= 0) throw new Exception("Error: Monto invalido");
        
        Coti n = new Coti(fec, val, 6.97);
        bd.add(n);
        return n;
    }
}