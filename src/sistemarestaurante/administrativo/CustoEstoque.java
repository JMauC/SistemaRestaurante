package sistemarestaurante.administrativo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sistemarestaurante.estoque.Ingrediente;
import sistemarestaurante.ferramentas.ConnectionFactory;

public class CustoEstoque {
    private int codigo;
    private String cnpjFornedor;
    private int codigoIngrediente;
    private Date data;
    private int qtdIngredientes;
    private double precoUnitario;
	

	/**
	 * Métodos de acesso ao banco de dados
	 */
	public void insereBanco() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
		String sql = "INSERT INTO custos_estoque (cnpj_fornecedor, " +
						"cod_ingrediente, qtd_ingredientes, preco_unitario) " +
						"VALUES (?,?,?,?);";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, cnpjFornedor);
		stmt.setInt(2, codigoIngrediente);
		stmt.setInt(3, qtdIngredientes);
		stmt.setDouble(4, precoUnitario);

        try {
			stmt.executeUpdate();
			Ingrediente.aumentaQtdEstoque(codigoIngrediente, qtdIngredientes);
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
		String sql = "SELECT data, cnpj_fornecedor, cod_ingrediente, " +
						"qtd_ingredientes, preco_unitario " +
						"FROM custos_estoque " +
						"ORDER BY data DESC, cnpj_fornecedor ASC;";
		PreparedStatement stmt = con.prepareStatement(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            ResultSet rs = stmt.executeQuery();

			System.out.printf("\n\n|Data\t- CNPJ Fornecedor - Ingrediente - Quantidade - " +
								"Preco Und. - Preco Total|\n");

            while(rs.next()){
				Date data = rs.getDate("data");
				String dataFormatada = sdf.format(data);
				String cnpj = rs.getString("cnpj_fornecedor");
				String ingrediente = Ingrediente.buscaNome(rs.getInt("cod_ingrediente"));
				int qtdIngrediente = rs.getInt("qtd_ingredientes");
				double precoUnitario = rs.getDouble("preco_unitario");
				double precoTotal = precoUnitario * qtdIngrediente;

                System.out.printf("|%s\t- %s\t- %s\t- %d\t- R$ %.2f\t- R$ %.2f|\n", 
									dataFormatada, cnpj, ingrediente, qtdIngrediente, 
									precoUnitario, precoTotal);
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
        String sql = "SELECT SUM(qtd_ingredientes * preco_unitario) FROM custos_estoque;";
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
	

    // Variavel codigo
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

    // Variavel cnpjFornedor
	public String getCnpjFornedor() {
		return cnpjFornedor;
	}
	public void setCnpjFornedor(String cnpjFornedor) {
		this.cnpjFornedor = cnpjFornedor;
	}

    // Variavel data
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
    }

    // Variavel codigoIngrediente
	public int getCodigoIngrediente() {
		return codigoIngrediente;
	}
	public void setCodigoIngrediente(int codigoIngrediente) {
		this.codigoIngrediente = codigoIngrediente;
	}
    
	// Variavel qtdIngredientes
	public int getQtdIngredientes() {
		return qtdIngredientes;
	}
	public void setQtdIngredientes(int qtdIngredientes) {
		this.qtdIngredientes = qtdIngredientes;
	}

    // Variavel precoUnitario
	public double getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
}