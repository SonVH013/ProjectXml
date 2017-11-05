package controller;

import controller.DBHandler;
import model.Product;
import model.ProductList;

import javax.swing.plaf.nimbus.State;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JaxBHandler {

    private static final JaxBHandler INSTANCE = new JaxBHandler();

    private JaxBHandler() {
    }

    public static JaxBHandler getINSTANCE() {
        return INSTANCE;
    }

    //readAllProductList
    public void readAllProductList() {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ProductList productList = new ProductList();
            productList = (ProductList) unmarshaller.unmarshal(new File("productlist.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void getAllProducts() {
        String sql = "select pc.Name, ProductID, p.Name, p.Color, p.StandardCost, p.Size, p.SellStartDate, p.SellEndDate, p.ModifiedDate\n" +
                "from AdventureWorksLT2008R2.SalesLT.Product p\n" +
                "JOIN AdventureWorksLT2008R2.SalesLT.ProductCategory pc\n" +
                "ON p.ProductCategoryID = pc.ProductCategoryID";
        //get results
        List<Product> list = getList(sql);
        ProductList productList = new ProductList();
        productList.setProductList(list);
        //writeToXml
        writeToXml(ProductList.class, productList, "productlist");
    }

    public void getProductsByCategory(String category) {
        String sql = "select pc.Name, ProductID, p.Name, p.Color, p.StandardCost, p.Size, p.SellStartDate, p.SellEndDate, p.ModifiedDate \n" +
                "from AdventureWorksLT2008R2.SalesLT.Product p \n" +
                "JOIN AdventureWorksLT2008R2.SalesLT.ProductCategory pc \n" +
                "ON p.ProductCategoryID = pc.ProductCategoryID\n" +
                "where pc.Name like '%" + category + "%'";
        //get results
        List<Product> list = getList(sql);
        ProductList productList = new ProductList();
        productList.setProductList(list);
        //writeToXML
        writeToXml(ProductList.class, productList, "productByCategory");

    }

    public void getProductsByPrice(double lowBound, double upBound) {
        String sql = "select pc.Name, ProductID, p.Name, p.Color, p.StandardCost, p.Size, p.SellStartDate, p.SellEndDate, p.ModifiedDate \n" +
                "from AdventureWorksLT2008R2.SalesLT.Product p \n" +
                "JOIN AdventureWorksLT2008R2.SalesLT.ProductCategory pc \n" +
                "ON p.ProductCategoryID = pc.ProductCategoryID\n" +
                "where p.StandardCost between " + lowBound + "and " + upBound;
        //get results
        List<Product> list = getList(sql);
        ProductList productList = new ProductList();
        productList.setProductList(list);
        //writeToXML
        writeToXml(ProductList.class, productList, "productByPrice");
    }

    public void addCategory(String category) {
        String sql = "insert into AdventureWorksLT2008R2.SalesLT.ProductCategory(Name)\n" +
                "  values('" + category + "')";
        Connection connection = DBHandler.getInstance().getConection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String category, String name, String color, double standardCost, double size,
                           String sellStartDate, String sellEndDate) {
        String sql = "insert into AdventureWorksLT2008R2.SalesLT.Product(ProductCategoryID, Name, ProductNumber,\n" +
                "Color, StandardCost, Size, SellStartDate, SellEndDate, ListPrice)\n" +
                "VALUES((select ProductCategoryID from AdventureWorksLT2008R2.SalesLT.ProductCategory where Name = '" + category + "' ),\n" +
                "'" + name + "', '" + name + "', '" + color + "', " + standardCost + ", " + size + ", '" + sellStartDate + "', '" + sellEndDate + "', 0)";
        Connection connection = DBHandler.getInstance().getConection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(String name, String color, double standardCost, double size,
                              String sellStartDate, String sellEndDate) {
        String sql = "update AdventureWorksLT2008R2.SalesLT.Product\n" +
                "set Name = ?, Color = ?, Size = ?, SellStartDate = ?, SellEndDate = ?, StandardCost = ?\n" +
                "where Name = ?";
        Connection connection = DBHandler.getInstance().getConection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, color);
            preparedStatement.setDouble(3, size);
            preparedStatement.setString(4, sellStartDate);
            preparedStatement.setString(5, sellEndDate);
            preparedStatement.setDouble(6, standardCost);
            preparedStatement.setString(7, name);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String name) {
        String sql = "delete from AdventureWorksLT2008R2.SalesLT.Product where Name = ?";
        Connection connection = DBHandler.getInstance().getConection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            int check = preparedStatement.executeUpdate();
            if (check != 0) {
                System.out.println("success");
            }
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Product> getList(String sql) {
        Statement statement = null;
        List<Product> list = null;
        Connection conn = DBHandler.getInstance().getConection();
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
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
            statement.close();
            resultSet.close();
            DBHandler.getInstance().closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
