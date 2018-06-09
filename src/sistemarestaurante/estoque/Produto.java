package sistemarestaurante.estoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sistemarestaurante.ferramentas.ConnectionFactory;

public class Produto{
    private int codigo;
    private String nome;
    private double preco;
    private boolean bebida;
    private ArrayList<Integer> listaIngredientes = new ArrayList<Integer>();
    private ArrayList<Integer> qtdCadaIngrediente = new ArrayList<Integer>();
    
    /**
     * Métodos anexação dos itens da lista de ingredientes
     */
    public void addInfoIngredientes(int codigoIngrediente, int quantidade){
        listaIngredientes.add(codigoIngrediente);
        qtdCadaIngrediente.add(quantidade);
    }

    /**
     * Métodos de classe
     */
    public static boolean verificaPossuiEstoque(int codProduto, int qtdProduto) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cod_ingrediente, qtd_ingrediente " +
                        "FROM produto_ingrediente WHERE cod_produto = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        int possuiEstoque = 0;

        stmt.setInt(1, codProduto);

        try {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int codIngrediente = rs.getInt("cod_ingrediente");
                int qtdIngrediente = rs.getInt("qtd_ingrediente");
                int qtdNecessaria = qtdIngrediente * qtdProduto;
                int qtdEstoque = Ingrediente.checaEstoque(codIngrediente);

                if(qtdNecessaria <= qtdEstoque){
                    possuiEstoque = possuiEstoque + 1;
                }
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }

        if(possuiEstoque == 0){
            return false;
        }
        return true;
    }


    public static void consomeProdutoEstoque(int codProduto) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cod_ingrediente, qtd_ingrediente " +
                        "FROM produto_ingrediente WHERE cod_produto = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setInt(1, codProduto);

        try {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int codigoIngrediente = rs.getInt("cod_ingrediente");
                int qtdIngrediente = rs.getInt("qtd_ingrediente");
                
                Ingrediente.diminuiQtdEstoque(codigoIngrediente, qtdIngrediente);
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


    public static void imprimeProdutos() throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT codigo, nome, preco FROM produtos;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|Codigo\t- Nome\t- Preco|\n");

            while(rs.next()){
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");

                System.out.printf("|%d\t- %s\t- %.2f|\n", codigo, nome, preco);
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


    public static String buscaNome(int codigo) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT nome FROM produtos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        String nome = null;

        stmt.setInt(1, codigo);

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


    public static double buscaPreco(int codigo) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT preco FROM produtos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        double preco = (float) 0.0;

        stmt.setInt(1, codigo);

        try {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                preco = rs.getFloat("preco");
            }

        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return preco;
    }


	/** 
     * GET's e SET's das variveis de classe
     */
	// Variavel codigo
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
    }
    // Variavel  nome
    public String getNome() {
		return nome;
	}
    public void setNome(String nome) {
		this.nome = nome;
    }
    // Variavel preco
    public double getPreco() {
		return preco;
	}
    public void setPreco(float preco) {
		this.preco = preco;
	}
    
    // Variabel bebida
	public boolean isBebida() {
		return bebida;
	}
    public void setBebida(boolean bebida) {
		this.bebida = bebida;
	}
    
    // ArrayList listaIngredientes
    public ArrayList<Integer> getListaIngredientes() {
        return listaIngredientes;
    }

    // ArrayList qtdCadaIngrediente
    public ArrayList<Integer> getQtdCadaIngrediente() {
        return qtdCadaIngrediente;
    }
}