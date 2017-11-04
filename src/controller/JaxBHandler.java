package controller;

import controller.DBHandler;
import model.Product;
import model.ProductList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JaxBHandler {

    private static final JaxBHandler INSTANCE = new JaxBHandler();

    private JaxBHandler() {
    }

    public static JaxBHandler getINSTANCE() {
        return INSTANCE;
    }

    public void writeToXml(Class newClass, Object object, String fileName) {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(newClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(object, new File(fileName + ".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readToXml() {

    }

    public void getAllProductList() {
        try {
            String sql = "select pc.Name, ProductID, p.Name, p.Color, p.StandardCost, p.Size, p.SellStartDate, p.SellEndDate, p.ModifiedDate\n" +
                    "from SalesLT.Product p\n" +
                    "JOIN SalesLT.ProductCategory pc\n" +
                    "ON p.ProductCategoryID = pc.ProductCategoryID";
            Statement statement = DBHandler.getInstance().getConection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Product> list = new ArrayList<>();
            while (resultSet.next()) {
                String category = resultSet.getString(1);
                String productId = resultSet.getString(2);
                String name = resultSet.getString(3);
                String color = resultSet.getString(4);
                String standradCost = resultSet.getString(5);
                String size = resultSet.getString(6);
                String sellStartDate = resultSet.getString(7);
                String sellEndDate = resultSet.getString(8);
                String modifiedDate = resultSet.getString(9);
                Product product = new Product(productId, name, color, standradCost, category, size, sellStartDate, sellEndDate, modifiedDate);
                list.add(product);
            }
            ProductList productList = new ProductList();
            productList.setProductList(list);
            writeToXml(ProductList.class, productList, "productlist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
