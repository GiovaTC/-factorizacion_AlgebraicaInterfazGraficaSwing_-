// modelo de datos .

public class ResultadoFactorizacion {

    private int id;
    private String metodo;
    private String expresion;
    private String resultado;

    public ResultadoFactorizacion() {
    }

    public ResultadoFactorizacion(String metodo,
                                  String expresion,
                                  String resultado
    ) {
        this.metodo = metodo;
        this.expresion = expresion;
        this.resultado = resultado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getExpresion() { return expresion; }
    public void setExpresion(String expresion) { this.expresion = expresion; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}
