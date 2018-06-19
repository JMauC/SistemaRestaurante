package sistemarestaurante.individuos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.individuos.Cliente;

public class Cargo{
	private int codigo;
	private String nome;


	/**
     * Métodos de acesso ao banco
     */
	public static String buscaNome(int codigo) throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT nome FROM cargos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        String nomeCargo = null;

        stmt.setInt(1, codigo);

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                nomeCargo = rs.getString("nome");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return nomeCargo;
	}


    /**
     * GET's e SET's das variaveis de classe
     */
	// Variavel codigo
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	// Variavel nome
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}