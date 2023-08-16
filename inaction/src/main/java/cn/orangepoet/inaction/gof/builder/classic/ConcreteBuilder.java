package cn.orangepoet.inaction.gof.builder.classic;

public class ConcreteBuilder implements Builder {

    private Product product = new Product();

    @Override
    public Product build() {
        return product;
    }

    @Override
    public void buildPart() {
        product.setName("name");
    }

}
