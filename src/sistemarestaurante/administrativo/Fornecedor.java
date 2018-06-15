package sistemarestaurante.administrativo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemarestaurante.ferramentas.ConnectionFactory;

public class Fornecedor {
    private String cnpj;
	private int codigo;
	private String razaoSocial;
	private String telefone;
    private String endereco;
    private String email;
	
	/**
	 * Métodos de acesso ao banco de dados
	 */
	public static void listaFornecedores() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
		String sql = "SELECT codigo, cnpj, razao_social, telefone, endereco, email " +
						"FROM fornecedores ORDER BY codigo;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|Codigo\t- CNPJ - Razao Social\t- Telefone - Endereco\t- e-mail|\n");

            while(rs.next()){
				int codigo = rs.getInt("codigo");
                String cnpj = rs.getString("cnpj");
                String razaoSocial = rs.getString("razao_social");
                String telefone = rs.getString("telefone");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");
                
                System.out.printf("|%d\t- %s\t\t- %s\t\t- %s\t- %s\t- %s|\n", 
                                    codigo, cnpj, razaoSocial, telefone, endereco, email);
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
	

	public void insereBanco() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
		String sql = "INSERT INTO fornecedores(cnpj, razao_social, " +
						"telefone, endereco, email) " +
						"VALUES (?,?,?,?,?);";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, cnpj);
		stmt.setString(2, razaoSocial);
		stmt.setString(3, telefone);
		stmt.setString(4, endereco);
		stmt.setString(5, email);

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

	
    /**
	 * GET's e SET's
	 */
    // Variavel cnpj
    public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	// Variavel codigo
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	// Variavel razaoSocial
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
    // Variavel telefone
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
    
    // Variavel endereco
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
    
    // Variavel email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}