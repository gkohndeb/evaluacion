package models;


import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;


//@Entity
public class Product implements PathBindable<Product>,
        QueryStringBindable<Product> {

    public static List<Product> products;

    //@Target({FIELD})
    //@Retention(RUNTIME)
    //@Constraint(validatedBy = EanValidator.class)
    @play.data.Form.Display(name="constraint.ean", attributes={"value"})
    public static @interface EAN {
        String message() default EanValidator.message;
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class EanValidator extends Constraints.Validator<String> implements ConstraintValidator<EAN, String> {
        final static public String message = "error.invalid.ean";

        public EanValidator() {}

        @Override
        public void initialize(EAN constraintAnnotation) {}

        @Override
        public boolean isValid(String value) {
            String pattern = "^[0-9]$";
            return value != null && value.matches(pattern);
        }

        @Override
        public F.Tuple<String, Object[]> getErrorMessageKey() {
            return new F.Tuple<String, Object[]>(message,
                    new Object[]{});
        }
    }


    static {
        products = new ArrayList<Product>();
        products.add(new Product("1", "Producto 1",
                "description 1"));
        products.add(new Product("2", "Producto 2",
                "description 2"));
        products.add(new Product("3", "Producto 3",
                " description 3"));
        products.add(new Product("4", "Producto 4",
                "description 4"));
        products.add(new Product("5", "Producto 5",
                " description 5"));
    }

    //@Id
    public Long id;
    @Constraints.Required
    //@EAN
    public String ean;
    @Constraints.Required
    public String name;
    public String description;



    //@ManyToMany
    public List<Insumo> insumos = new LinkedList<Insumo>();



    public Product() {
        // Left empty
    }

    public Product(String ean, String name, String description) {
        this.ean = ean;
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return String.format("%s - %s", ean, name);
    }

    public static List<Product> findAll() {
        return new ArrayList<Product>(products);
    }

    public static Product findByEan(String ean) {
        for (Product candidate : products) {
            if (candidate.ean.equals(ean)) {
                return candidate;
            }
        }
        return null;
    }

    public static List<Product> findByName(String term) {
        final List<Product> results = new ArrayList<Product>();
        for (Product candidate : products) {
            if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
                results.add(candidate);
            }
        }

        return results;
    }

    public static boolean remove(Product product) {
        return products.remove(product);
    }

    public void save() {
        products.remove(findByEan(this.ean));
        products.add(this);
    }

    @Override
    public Product bind(String key, String value) {
        return findByEan(value);
    }

    @Override
    public F.Option<Product> bind(String key, Map<String, String[]> data) {
        return F.Option.Some(findByEan(data.get("ean")[0]));
    }

    @Override
    public String unbind(String s) {
        return this.ean;
    }

    @Override
    public String javascriptUnbind() {
        return this.ean;
    }
}