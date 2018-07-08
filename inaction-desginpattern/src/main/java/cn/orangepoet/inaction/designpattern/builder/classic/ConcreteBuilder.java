package cn.orangepoet.inaction.designpattern.builder.classic;

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
