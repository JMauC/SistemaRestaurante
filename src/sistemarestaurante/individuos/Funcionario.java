package sistemarestaurante.individuos;

public class Funcionario extends Pessoa{
    private String carteiraDeTrabalho;
    private String senha;
    private double salario;
    private boolean turnoDiurno;
    protected void setCarteiraDeTrabalho(String este)
    {
        this.carteiraDeTrabalho=este;
    }
    protected String getCarteiraDeTrabalho()
    {
        return this.carteiraDeTrabalho;
    }
    private void setSenha(String este)
    {
        this.senha=este;
    }
    private String getSenha()
    {
        return this.senha;
    }
    protected void setSalario(double este)
    {
        this.salario=este;
    }
    protected double getSalario()
    {
        return this.salario;
    }
    protected void setTurno(boolean este)
    {
        this.turnoDiurno=este;
    }
    protected boolean getTurno()
    {
        return this.turnoDiurno;
    }
}
