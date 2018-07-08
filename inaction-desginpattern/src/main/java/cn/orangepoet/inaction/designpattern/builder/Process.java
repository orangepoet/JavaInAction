package cn.orangepoet.inaction.designpattern.builder;


import cn.orangepoet.inaction.designpattern.builder.effective.NutritionFacts;
import cn.orangepoet.inaction.designpattern.builder.classic.Builder;
import cn.orangepoet.inaction.designpattern.builder.classic.ConcreteBuilder;
import cn.orangepoet.inaction.designpattern.builder.classic.Director;
import cn.orangepoet.inaction.designpattern.builder.classic.Product;

/**
 * The builder designpattern is an object creation software design patter. Unlike
 * Abstract Factory designpattern or factory method designpattern whose intension is to use
 * polymorphic, its intension is to find a solution to telescope constructor
 * anti-designpattern; The telescope constructor anti-designpattern occurs when the increase
 * of constructor parameters combination leads to an exponential list of
 * constructor. Instead of using numerous constructors, the builder designpattern uses
 * another builder object, a builder, that receives each initialization
 * parameters step by step and then return constructed object at once;
 *
 * @author orange
 */
public class Process {

    /**
     * A example from <Effective Java>
     */
    private static NutritionFacts getNutritionFact() {
        NutritionFacts nutritionFacts = new NutritionFacts.Builder(0, 0)
                .calorise(100).fact(32).sodium(23).build();
        System.out.println(nutritionFacts.toString());
        return nutritionFacts;
    }

    /**
     * Classic builder designpattern
     */
    private static Product getProduct() {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.constructor();
        System.out.println(product.toString());
        return product;
    }

    public static void main(String[] args) {
        getProduct();
        getNutritionFact();
    }
}
