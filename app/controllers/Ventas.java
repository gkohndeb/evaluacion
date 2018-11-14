package controllers;


import models.Product;
import models.Venta;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ventas.detalledeventa;

import java.util.LinkedList;
import java.util.List;

public class Ventas extends Controller {

    private static final Form<Venta> ventaForm = Form.form(Venta.class);
    private static  final List<Product> productos = new LinkedList<Product>();


    public static Result newVenta() {


          return ok(detalledeventa.render(ventaForm, productos));
    }

    public static Result save() {

        Form<Venta> boundForm = ventaForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
           return badRequest(detalledeventa.render(boundForm, productos));
        }

        Venta venta = boundForm.get();
        venta.save();
        flash("success",
                String.format("Successfully added Venta %s", venta));

        return redirect(routes.Ventas.newVenta());
    }
}
