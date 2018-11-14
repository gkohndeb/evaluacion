package models;


import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import java.util.List;
import java.util.Map;

//@Entity
public class Venta implements PathBindable<Venta>,
        QueryStringBindable<Venta> {

    public static List<Venta> ventas;

   // @Id
    public String id;
    public String comprador;
    public String cantidad;
    public String producto;

    public Venta() {
        // Left empty
    }

    public Venta(String id, String comprador, String cantidad, String producto) {
        this.id = id;
        this.comprador = comprador;
        this.cantidad = cantidad;
        //this.producto = Product.name;
    }

    public String toString() {
        return String.format("%s - %s - %s - %s", id, comprador, cantidad, producto);
    }


    public static Venta findById(String id) {
        for (Venta candidate : ventas) {
            if (candidate.id.equals(id)) {
                return candidate;
            }
        }
        return null;
    }


    public void save() {
        ventas.add(this);

    }


    @Override
    public Venta bind(String key, String value) {
        return findById(value);
    }

    @Override
    public F.Option<Venta> bind(String key, Map<String, String[]> data) {
        return F.Option.Some(findById(data.get("id")[0]));
    }

    @Override
    public String unbind(String s) {
        return this.id;
    }

    @Override
    public String javascriptUnbind() {
        return this.id;
    }
}






