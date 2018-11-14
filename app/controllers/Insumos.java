package controllers;
import models.Insumo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.insumos.detalledeinsumos;
import views.html.insumos.listadeinsumos;

import java.util.List;




public class Insumos extends Controller {


    private static final Form<Insumo> insumoForm = Form.form(Insumo.class);

    public static Result list() {
        List<Insumo> insumos = Insumo.findAll();
        return ok(listadeinsumos.render(insumos));
    }

    public static Result newInsumo() {
        return ok(detalledeinsumos.render(insumoForm));
    }

    public static Result details(Insumo insumo) {
        Form<Insumo> filledForm = insumoForm.fill(insumo);
        return ok(detalledeinsumos.render(filledForm));
    }

    public static Result save() {

        Form<Insumo> boundForm = insumoForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(detalledeinsumos.render(boundForm));
        }

        Insumo insumo = boundForm.get();
        insumo.save();
        flash("success",
                String.format("Successfully added Insumo %s", insumo));

        return redirect(routes.Insumos.list());
    }

    public static Result delete(String id) {
        final Insumo insumo = Insumo.findById(id);
        if(insumo == null) {
            return notFound(String.format("Insumo %s does not exists.", id));
        }
        Insumo.remove(insumo);
        return redirect(routes.Insumos.list());
    }
}