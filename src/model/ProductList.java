package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProductList {

    private List<Product> productList;

    public ProductList() {
    }

    public ProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @XmlElement(name = "Product")
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
