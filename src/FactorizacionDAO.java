import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactorizacionDAO {

    public void guardar(ResultadoFactorizacion r) {

        try (Connection cn = ConexionDB.getConexion()) {
            String sql = """
                    INSERT INTO FACTORIZACIONES
                    (METODO, EXPRESION, RESULTADO)
                    VALUES (?, ?, ?)
                    """;

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, r.getMetodo());
            ps.setString(2, r.getExpresion());
            ps.setString(3, r.getResultado());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<ResultadoFactorizacion> listar() {

        List<ResultadoFactorizacion> lista =
                new ArrayList<>();

        try (Connection cn = ConexionDB.getConexion()) {

            String sql =
                    "SELECT * FROM FACTORIZACIONES";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ResultadoFactorizacion r = new ResultadoFactorizacion();

                r.setId(rs.getInt("ID"));
                r.setMetodo(rs.getString("METODO"));
                r.setExpresion(rs.getString("EXPRESION"));
                r.setExpresion(rs.getString("RESULTADO"));

                lista.add(r);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}
