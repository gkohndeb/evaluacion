package models;

//import play.db.ebean.Model;

import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import javax.persistence.*;

//@Entity
public class Insumo  implements PathBindable<Insumo>,
        QueryStringBindable<Insumo> {

    public static List<Insumo> insumos;

    static {
        insumos = new ArrayList<Insumo>();
        insumos.add(new Insumo("1", "Insumo 1", "100"));
        insumos.add(new Insumo("2", "Insumo 2", "200"));
        insumos.add(new Insumo("3", "Insumo 3", "300"));
        insumos.add(new Insumo("4", "Insumo 4", "400"));
        insumos.add(new Insumo("5", "Insumo 5", "500"));
    }

    //@Id
    @Constraints.Required
    public String id;
    @Constraints.Required
    public String name;
    public String cantidad;



    //@ManyToMany
  // public List<Insumo> insumos = new LinkedList<Insumo>();



    public Insumo() {
        // Left empty
    }

    public Insumo(String id, String name, String cantidad) {
        this.id = id;
        this.name = name;
        this.cantidad = cantidad;
    }

    public String toString() {
        return String.format("%s - %s", id, name, cantidad);
    }

    public static List<Insumo> findAll() {
        return new ArrayList<Insumo>(insumos);
    }

    public static Insumo findById(String id) {
        for (Insumo candidate : insumos) {
            if (candidate.id.equals(id)) {
                return candidate;
            }
        }
        return null;
    }

    public static List<Insumo> findByName(String term) {
        final List<Insumo> results = new ArrayList<Insumo>();
        for (Insumo candidate : insumos) {
            if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
                results.add(candidate);
            }
        }

        return results;
    }

    public static boolean remove(Insumo insumo) {

        return insumos.remove(insumo);
    }

    public void save() {
        insumos.remove(findById(this.id));
        insumos.add(this);
    }

    @Override
    public Insumo bind(String key, String value) {
        return findById(value);
    }

    @Override
    public F.Option<Insumo> bind(String key, Map<String, String[]> data) {
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