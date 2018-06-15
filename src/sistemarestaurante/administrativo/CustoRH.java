package sistemarestaurante.administrativo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.individuos.Funcionario;

public class CustoRH {
    private int codigo;
    private int codigoFuncionario;
    private Date data;
    private double salario;
	

	/**
	 * Métodos de acesso ao banco de dados
	 */
	public static void insereBanco(String cpfFuncionario) throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
		String sql = "INSERT INTO custos_rh (cpf_funcionario, salario) " +
						"VALUES (?,?);";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, cpfFuncionario);
		stmt.setDouble(2, Funcionario.buscaSalario(cpfFuncionario));

        try {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
	}
	

	public static void listaCustos() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
		String sql = "SELECT data, cpf_funcionario, salario " +
						"FROM custos_rh " +
						"ORDER BY data DESC, cpf_funcionario ASC;";
		PreparedStatement stmt = con.prepareStatement(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            ResultSet rs = stmt.executeQuery();

			System.out.printf("\n\n|Data\t- CPF\t\t- Nome do funcionario\t- Salario|\n");

            while(rs.next()){
				Date data = rs.getDate("data");
				String dataFormatada = sdf.format(data);
				String cpf = rs.getString("cpf_funcionario");
				String nome = Funcionario.buscaNome(cpf);
				double salario = rs.getDouble("salario");

                System.out.printf("|%s\t- %s\t\t- %s\t- R$ %.2f|\n", 
									dataFormatada, cpf, nome, salario);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
	}


	public static double buscaCustoTotal() throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT SUM(salario) FROM custos_rh;";
        PreparedStatement stmt = con.prepareStatement(sql);
        double custoTotal = 0.0;

        try {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                custoTotal = rs.getFloat("sum");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return custoTotal;
	}
	

    /**
	 * GET's e SET's
	 */
    // Variavel codigo
     public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

    // Variavel codigoFuncionario
	public int getCodigoFuncionario() {
		return codigoFuncionario;
	}
	public void setCodigoFuncionario(int codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}

    // Variavel data
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

    // Variavel salario
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
}