// clase conexion .

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection getConexion() {

        Connection cn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Conexion ok oracle! ");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cn;
    }
}
