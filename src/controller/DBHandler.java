package controller;
import model.Product;
import model.ProductList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {

    private static final DBHandler INSTANCE = new DBHandler();

    private DBHandler() {

    }

    public static DBHandler getInstance() {
        return INSTANCE;
    }

    public Connection getConection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(
                    "jdbc:sqlserver://10.211.55.4\\MSSQLSERVER:1433;database=AdventureWorksLT2008R2","sa", "123456");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
