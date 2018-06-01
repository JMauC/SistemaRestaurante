package sistemarestaurante.servico;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sistemarestaurante.estoque.Produto;
import sistemarestaurante.ferramentas.ConnectionFactory;

public class Pedido{
    private int codigo;
    private int codigoMesa;
    private String cpfGarcom;
    private ArrayList<Integer> produtosPedidos = new ArrayList<Integer>();
    private ArrayList<Integer> qtdProdutosPedidos = new ArrayList<Integer>();
    private boolean pedidoPronto;
    
    /**
     * Método para adicionar produto ao pedido da mesa
     */
    public void addItemPedido(int codigoProduto, int quantidade){
        produtosPedidos.add(codigoProduto);
        qtdProdutosPedidos.add(quantidade);
	}

	/**
     * Métodos de classe
     */
    public static void finalizaPedido(int codigo) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT * FROM pedidos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setInt(1, codigo);

        try {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Array a = rs.getArray("lista_produtos");
                Integer[] codigoProduto = (Integer[]) a.getArray();
                Array b = rs.getArray("qtd_produtos");
                Integer[] qtdProduto = (Integer[]) b.getArray();
                
                for(int i = 0; i < qtdProduto.length; i++){
                    Produto.consomeProdutoEstoque(codigoProduto[i]);;
				}
				
			}
			
			marcaPedidoPronto(codigo);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
	}
	
	public static void marcaPedidoPronto(int codigo) throws SQLException{
		Connection con = new ConnectionFactory().getConexao();
        String query = "UPDATE pedidos SET pedido_pronto = true " +
                            "WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(query);
        
        stmt.setInt(1, codigo);

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
     * Métodos de acesso ao banco de dados
     */
    public void insereBanco() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String query = "INSERT INTO pedidos " +
                            "(codigo_mesa, cpf_garcom, lista_produtos, qtd_produtos) " +
                            "VALUES(?, ?, ?, ?);";
        PreparedStatement stmt = con.prepareStatement(query);
        
		stmt.setInt(1, codigoMesa);
        stmt.setString(2, cpfGarcom);
        stmt.setArray(3, con.createArrayOf("integer", produtosPedidos.toArray()));
        stmt.setArray(4, con.createArrayOf("integer", qtdProdutosPedidos.toArray()));

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

	// Variavel codigo
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

    // Variavel codigoMesa
	public int getCodigoMesa() {
		return codigoMesa;
	}
	public void setCodigoMesa(int codigoMesa) {
		this.codigoMesa = codigoMesa;
	}
    
	// Variavel cpfGarcom
	public String getCpfGarcom() {
		return cpfGarcom;
	}
	public void setCpfGarcom(String cpfGarcom) {
		this.cpfGarcom = cpfGarcom;
	}
    
    //Variavel produtosPedidos
	public ArrayList<Integer> getProdutosPedidos() {
		return produtosPedidos;
	}

    //Variavel qtdProdutosPedidos
	public ArrayList<Integer> getQtdProdutosPedidos() {
		return qtdProdutosPedidos;
	}

	// Variavel pedidoPronto
	public boolean isPedidoPronto() {
		return pedidoPronto;
	}
	public void setPedidoPronto(boolean pedidoPronto) {
		this.pedidoPronto = pedidoPronto;
	}
}