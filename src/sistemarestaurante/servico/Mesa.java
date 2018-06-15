package sistemarestaurante.servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.individuos.Cliente;

public class Mesa{
	private int codigo;
	private int andar;
	private boolean ocupada;
	private String cpfCliente;


	/**
     * Métodos de acesso ao banco
     */
	public static int buscaMesaLivre() throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT codigo FROM mesas WHERE ocupada = false;";
        PreparedStatement stmt = con.prepareStatement(sql);
        int codigoMesa = 0;

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                codigoMesa = rs.getInt("codigo");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return codigoMesa;
	}


	public static void ocupaMesa(int codigoMesa) throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
		String sql = "UPDATE mesas SET ocupada = true " +
						"WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, codigoMesa);

        try {
			if(stmt.executeUpdate() > 0) {
				System.out.printf("\nA mesa %d foi ocupada pelo cliente.\n", codigoMesa);
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
	

	public static void ocupaMesa(int codigoMesa, String cpf) throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
		String sql = "UPDATE mesas SET ocupada = true, cpf_ocupante = ? " +
						"WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, cpf);
		stmt.setInt(2, codigoMesa);

        try {
			if(stmt.executeUpdate() > 0){
			
			System.out.printf("\nA mesa %d foi ocupada pelo cliente %s, CPF %s.\n",
								codigoMesa, Cliente.buscaNome(cpf), cpf);
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


	public static void desocupaMesa(int codigoMesa) throws SQLException {
		Connection con = new ConnectionFactory().getConexao();
		String sql = "UPDATE mesas SET ocupada = false, cpf_ocupante = null " +
						"WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, codigoMesa);

        try {
			stmt.executeUpdate();
			
			System.out.printf("\nA mesa %d foi desocupada.\n", codigoMesa);
		}            
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
	}


	public static boolean verifMesaOcupada(int codigo) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT ocupada FROM mesas WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        boolean isOcupada = false;

        stmt.setInt(1, codigo);

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                isOcupada = rs.getBoolean("ocupada");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return isOcupada;
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

	// Variavel codigo
	public int getAndar() {
		return andar;
	}
	public void setAndar(int andar) {
		this.andar = andar;
	}

	//Variavel ocupada
	public boolean isOcupada() {
		return ocupada;
	}
	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	// Variavel cpfCliente
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
}