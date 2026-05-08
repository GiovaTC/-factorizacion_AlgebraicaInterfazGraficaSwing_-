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