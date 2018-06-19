package sistemarestaurante.ferramentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import sistemarestaurante.ferramentas.ConnectionFactory;

public class Login {
    private String cpfUsuario;
    private String senha;
    private String nome;
    private int codigoCargo;
    
    public int realizaLogin() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT nome, cargo FROM funcionarios WHERE cpf = ? AND senha = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println();
        System.out.println("Digite seu cpf para logar ou \"sair\" para fechar o programa.");

        System.out.print("Usuario: ");
        setCpfUsuario(input.nextLine());
        //System.out.print(getCpfUsuario());
        if (cpfUsuario.intern() == "sair"){
            return -1;
        }
        System.out.print("Senha: ");
        setSenha(input.nextLine());
        //input.close();
        
        stmt.setString(1, getCpfUsuario());
        stmt.setString(2, getSenha());

        try {
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                setNome(rs.getString("nome"));
                setCodigoCargo(rs.getInt("cargo"));

                System.out.printf("Seja bem vindo, %s!\n", getNome());
            }
            else{
                System.out.println("\nUsuario/senha nao localizados!");
                setCodigoCargo(0);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }

        if(getCodigoCargo() == 0){
            return this.realizaLogin(); // Recursividade caso haja falha ao localizar login ou senha
        }
        return getCodigoCargo();
    }
    
	/**
	 * GET's e SET's das variaveis de classe
	 */
    //Variavel cpfUsuario
	public String getCpfUsuario() {
		return cpfUsuario;
	}
	private void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
    }
    
    // Variavel senha
	private String getSenha() {
		return senha;
	}
	private void setSenha(String senha) {
		this.senha = senha;
	}

    // Variavel nome
	private String getNome() {
		return nome;
	}
	private void setNome(String nome) {
		this.nome = nome;
	}

	// Variavel codigoCargo
	private int getCodigoCargo() {
		return codigoCargo;
	}
	private void setCodigoCargo(int codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
}