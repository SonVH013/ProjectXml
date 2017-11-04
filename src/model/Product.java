package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"product_category", "id", "name", "color", "standard_code", "size", "sellStartDate", "sellEndDate", "modifyDate"} )
public class Product {
    private String id;
    private String name;
    private String color;
    private String standard_code;
    private String product_category;
    private String size;
    private String sellStartDate;
    private String sellEndDate;
    private String modifyDate;

    public Product() {
    }

    public Product(String id, String name, String color, String standard_code, String product_category, String size, String sellStartDate, String sellEndDate, String modifyDate) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.standard_code = standard_code;
        this.product_category = product_category;
        this.size = size;
        this.sellStartDate = sellStartDate;
        this.sellEndDate = sellEndDate;
        this.modifyDate = modifyDate;
    }

    public String getId() {
        return id;
    }

    @XmlElement(name = "Id")
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    @XmlElement(name = "Color")
    public void setColor(String color) {
        this.color = color;
    }

    public String getStandard_code() {
        return standard_code;
    }

    @XmlElement(name = "Standard_code")
    public void setStandard_code(String standard_code) {
        this.standard_code = standard_code;
    }

    public String getProduct_category() {
        return product_category;
    }

    @XmlElement(name = "Category")
    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getSize() {
        return size;
    }

    @XmlElement(name = "Size")
    public void setSize(String size) {
        this.size = size;
    }

    public String getSellStartDate() {
        return sellStartDate;
    }

    @XmlElement(name = "SellStartDate")
    public void setSellStartDate(String sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public String getSellEndDate() {
        return sellEndDate;
    }

    @XmlElement(name = "SellEndDate")
    public void setSellEndDate(String sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    @XmlElement(name = "ModifyDate")
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}
