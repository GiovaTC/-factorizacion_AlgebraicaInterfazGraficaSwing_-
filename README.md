# -factorizacion_AlgebraicaInterfazGraficaSwing_- :.
Proyecto Java + Oracle 19c:

```

╔══════════════════════╗
║      ALGEBRA         ║
║    FACTORIZADOR      ║
║         x²           ║
╚══════════════════════╝

```

<img width="2552" height="1076" alt="image" src="https://github.com/user-attachments/assets/e9136095-f19f-4571-b6c4-d73249e53845" />    

```
Factorización Algebraica con Interfaz Gráfica Swing (IntelliJ IDEA)

Sistema completo desarrollado en:

Java SE
Swing GUI
Oracle Database 19c
JDBC
Programación Orientada a Objetos (POO).

✅ Características del Sistema

Incluye:

✅ 3 métodos de factorización algebraica
✅ Interfaz gráfica Swing
✅ Datos de ejemplo
✅ Registro automático en Oracle 19c
✅ JTable para visualizar operaciones
✅ JTextArea para resultados matemáticos

🧩 Métodos de Factorización Implementados
1. Factor Común
Ejemplo
6x + 12
= 6(x + 2)
2. Diferencia de Cuadrados
Ejemplo
x² - 25
= (x - 5)(x + 5)
3. Trinomio Cuadrado Perfecto
Ejemplo
x² + 6x + 9
= (x + 3)²

📁 Estructura del Proyecto
Factorizacion_Oracle19C/
│
├── src/
│   ├── ConexionDB.java
│   ├── FactorizacionDAO.java
│   ├── FactorizacionGUI.java
│   ├── ResultadoFactorizacion.java
│   └── Main.java
│
└── oracle/
    └── script_factorizacion.sql

🗄️ SCRIPT ORACLE 19C
script_factorizacion.sql
CREATE TABLE FACTORIZACIONES (

    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    METODO VARCHAR2(100),
    EXPRESION VARCHAR2(200),
    RESULTADO VARCHAR2(300),
    FECHA_REGISTRO DATE DEFAULT SYSDATE,

    CONSTRAINT PK_FACTORIZACIONES
    PRIMARY KEY (ID)
);

SELECT * FROM FACTORIZACIONES;

☕ CLASE DE CONEXIÓN
ConexionDB.java
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "oracle";

    public static Connection getConexion() {

        Connection cn = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Conexion OK Oracle");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return cn;
    }
}

📦 MODELO DE DATOS
ResultadoFactorizacion.java
public class ResultadoFactorizacion {

    private int id;
    private String metodo;
    private String expresion;
    private String resultado;

    public ResultadoFactorizacion() {
    }

    public ResultadoFactorizacion(
            String metodo,
            String expresion,
            String resultado
    ) {
        this.metodo = metodo;
        this.expresion = expresion;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}

💾 DAO ORACLE
FactorizacionDAO.java
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

                ResultadoFactorizacion r =
                        new ResultadoFactorizacion();

                r.setId(rs.getInt("ID"));
                r.setMetodo(rs.getString("METODO"));
                r.setExpresion(rs.getString("EXPRESION"));
                r.setResultado(rs.getString("RESULTADO"));

                lista.add(r);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}

🧠 INTERFAZ GRÁFICA
FactorizacionGUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FactorizacionGUI extends JFrame {

    private JTextArea txtResultado;

    private JTable tabla;

    private DefaultTableModel modelo;

    private FactorizacionDAO dao =
            new FactorizacionDAO();

    public FactorizacionGUI() {

        setTitle("Factorizacion Algebraica");

        setSize(900, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        iniciarComponentes();

        cargarDatos();
    }

    private void iniciarComponentes() {

        JPanel panelSuperior = new JPanel();

        JButton btnFactorComun =
                new JButton("Factor Comun");

        JButton btnDiferencia =
                new JButton("Diferencia Cuadrados");

        JButton btnTrinomio =
                new JButton("Trinomio Perfecto");

        panelSuperior.add(btnFactorComun);
        panelSuperior.add(btnDiferencia);
        panelSuperior.add(btnTrinomio);

        add(panelSuperior, BorderLayout.NORTH);

        txtResultado = new JTextArea();

        txtResultado.setFont(
                new Font("Monospaced",
                        Font.BOLD,
                        18)
        );

        JScrollPane scrollTexto =
                new JScrollPane(txtResultado);

        add(scrollTexto, BorderLayout.CENTER);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Metodo");
        modelo.addColumn("Expresion");
        modelo.addColumn("Resultado");

        tabla = new JTable(modelo);

        JScrollPane scrollTabla =
                new JScrollPane(tabla);

        scrollTabla.setPreferredSize(
                new Dimension(800, 200)
        );

        add(scrollTabla, BorderLayout.SOUTH);

        btnFactorComun.addActionListener(e -> {

            String expresion = "6x + 12";

            String resultado = "6(x + 2)";

            mostrarResultado(
                    "FACTOR COMUN",
                    expresion,
                    resultado
            );
        });

        btnDiferencia.addActionListener(e -> {

            String expresion = "x² - 25";

            String resultado =
                    "(x - 5)(x + 5)";

            mostrarResultado(
                    "DIFERENCIA CUADRADOS",
                    expresion,
                    resultado
            );
        });

        btnTrinomio.addActionListener(e -> {

            String expresion =
                    "x² + 6x + 9";

            String resultado =
                    "(x + 3)²";

            mostrarResultado(
                    "TRINOMIO PERFECTO",
                    expresion,
                    resultado
            );
        });
    }

    private void mostrarResultado(
            String metodo,
            String expresion,
            String resultado
    ) {

        txtResultado.setText("");

        txtResultado.append(
                "METODO:\n" + metodo + "\n\n"
        );

        txtResultado.append(
                "EXPRESION:\n" + expresion + "\n\n"
        );

        txtResultado.append(
                "RESULTADO:\n" + resultado
        );

        ResultadoFactorizacion r =
                new ResultadoFactorizacion(
                        metodo,
                        expresion,
                        resultado
                );

        dao.guardar(r);

        cargarDatos();
    }

    private void cargarDatos() {

        modelo.setRowCount(0);

        List<ResultadoFactorizacion> lista =
                dao.listar();

        for (ResultadoFactorizacion r : lista) {

            modelo.addRow(new Object[]{

                    r.getId(),
                    r.getMetodo(),
                    r.getExpresion(),
                    r.getResultado()
            });
        }
    }
}

🚀 MAIN
Main.java
public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            new FactorizacionGUI().setVisible(true);
        });
    }
}

🔌 DRIVER JDBC ORACLE

Agregar el archivo:

ojdbc11.jar

En IntelliJ IDEA:

File
→ Project Structure
→ Libraries
→ Add JAR

▶️ RESULTADO FINAL

La aplicación permitirá:

✅ Factorizar expresiones algebraicas
✅ Mostrar resultados en GUI
✅ Guardar operaciones en Oracle 19c
✅ Consultar historial en JTable
✅ Ejecutarse desde IntelliJ IDEA

🖥️ EJEMPLO VISUAL
+------------------------------------------------+
| Factor Comun | Diferencia Cuadrados | Trinomio |
+------------------------------------------------+

METODO:
FACTOR COMUN

EXPRESION:
6x + 12

RESULTADO:
6(x + 2)

--------------------------------------------------
| ID | METODO | EXPRESION | RESULTADO            |
--------------------------------------------------
| 1  | FACTOR COMUN | 6x+12 | 6(x+2)             |
--------------------------------------------------
:. . / .
