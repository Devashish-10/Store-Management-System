import java.sql.*;

class DBConnection {
    private String url = "jdbc:mysql://localhost:3306/storedb";
    private String user = "username";
    private String pass = "password";
    private Connection connection;

    public DBConnection(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
