package sistemarestaurante.individuos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import sistemarestaurante.ferramentas.ConnectionFactory;

public class Funcionario extends Pessoa {
    private String ctps;
    private double salario;
    private boolean turnoDiurno;
    private String senha;
    private int codigoCargo;
    

    /**
     * Métodos de acesso ao banco de dados
     */
    public void insereBanco() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "INSERT INTO funcionarios" +
                            "(cpf, nome, rg, data_nascimento, filiacao_pai, filiacao_mae, " +
                            "naturalidade, estado_civil, sexo_masculino, telefone, email, " +
                            "endereco, escolaridade, ctps, salario, turno_diurno, senha, cargo) " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        stmt.setString(1, getCpf());
        stmt.setString(2, getNome());
        stmt.setString(3, getRg());
        stmt.setDate(4, java.sql.Date.valueOf(formatter.format(getDataNascimento())));
        stmt.setString(5, getFiliacaoPai());
        stmt.setString(6, getFiliacaoMae());
        stmt.setString(7, getNaturalidade());
        stmt.setString(8, getEstadoCivil());
        stmt.setBoolean(9, isSexoMasculino());
        stmt.setString(10, getTelefone());
        stmt.setString(11, getEmail());
        stmt.setString(12, getEndereco());
        stmt.setString(13, getEscolaridade());
        stmt.setString(14, getCtps());
        stmt.setDouble(15, getSalario());
        stmt.setBoolean(16, isTurnoDiurno());
        stmt.setString(17, getSenha());
        stmt.setInt(18, getCodigoCargo());

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


    public static void listaFuncionarios() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cpf, nome, salario, turno_diurno, cargo " +
                        "FROM funcionarios ORDER BY nome " +
                        "ORDER BY nome;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|CPF\t- Nome\t- Salario\t- Turno\t- Cargo|\n");

            while(rs.next()){
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                double salario = rs.getDouble("salario");
                String cargo = rs.getString("cargo");
                char turno = 'N';
                
                if(rs.getBoolean("turno_diurno")){
                    turno = 'D';
                }

                System.out.printf("|%s\t- %s\t- %.2f\t- %c\t- %s|\n", 
                                    cpf, nome, salario, turno, cargo);
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


    public static void consultaProdutividade() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT f.cpf, f.nome, COUNT(p.codigo) " +
                        "FROM funcionarios AS f " +
                            "INNER JOIN pedidos AS p " +
                            "ON f.cpf = p.cpf_garcom " +
                        "GROUP BY f.cpf ORDER BY f.nome;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|CPF\t- Nome\t\t- Pedidos atendidos|\n");

            while(rs.next()){
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                int qtdPedidos = rs.getInt("count");
                
                System.out.printf("|%s\t- %s\t\t- %d|\n", cpf, nome, qtdPedidos);
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


    public static String buscaNome(String cpf) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT nome FROM funcionarios WHERE cpf = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        String nome = null;

        stmt.setString(1, cpf);

        try {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                nome = rs.getString("nome");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return nome;
    }


    public static double buscaSalario(String cpf) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT salario FROM funcionarios WHERE cpf = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        double salario = 0.0;

        stmt.setString(1, cpf);

        try {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                salario = rs.getDouble("salario");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return salario;
    }


    public static int buscaCargo(String cpf) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cargo FROM funcionarios WHERE cpf = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        int cargo = 0;

        stmt.setString(1, cpf);

        try {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                cargo = rs.getInt("cargo");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return cargo;
    }


    public static void removeBanco(String cpf) throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "DELETE FROM funcionarios WHERE cpf = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setString(1, cpf);

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
     * GET's e SET's das variaveis de classe
     */
    // Variavel ctps
	public String getCtps() {
		return ctps;
	}
	public void setCtps(String ctps) {
		this.ctps = ctps;
	}
    
    // Variavel salario
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
    
    // Variavel turnoDiurno
	public boolean isTurnoDiurno() {
		return turnoDiurno;
	}
	public void setTurnoDiurno(boolean turnoDiurno) {
		this.turnoDiurno = turnoDiurno;
	}
    
    // Variavel senha
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
    
    // Variavel codigoCargo
	public int getCodigoCargo() {
		return codigoCargo;
	}
	public void setCodigoCargo(int codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
}