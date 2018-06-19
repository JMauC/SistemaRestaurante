package sistemarestaurante.individuos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import sistemarestaurante.ferramentas.ConnectionFactory;

public class Cliente extends Pessoa {

    public void insereBanco() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "INSERT INTO clientes" +
                            "(cpf, nome, rg, data_nascimento, filiacao_pai, filiacao_mae," +
                            "naturalidade, estado_civil, sexo_masculino, telefone, email," +
                            "endereco, escolaridade, profissao) " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
        stmt.setString(14, getProfissao());
        
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

        public static void listaClientes() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cpf, nome, rg, sexo_masculino, email FROM clientes ORDER BY nome;";
                        //"FROM funcionarios ORDER BY nome " +
                        //"ORDER BY nome;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|CPF\t\t- Nome\t\t- RG\t\t- Sexo\t- E-mail|\n");

            while(rs.next()){
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String rg = rs.getString("rg");
                String email = rs.getString("email");
                char sexo = 'F';
                
                if(rs.getBoolean("sexo_masculino")){
                    sexo = 'M';
                }

                System.out.printf("|%s\t- %s\t- %s\t- %c\t- %s|\n", 
                                    cpf, nome, rg, sexo, email);
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
        String sql = "SELECT nome FROM clientes WHERE cpf = ?;";
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


    public static boolean verifCadastroExiste(String cpf) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cpf FROM clientes WHERE cpf = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        boolean cadastroExiste = false;

        stmt.setString(1, cpf);

        try {
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                cadastroExiste = true;
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return cadastroExiste;
    }
}