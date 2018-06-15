package sistemarestaurante.servico;

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
    private String cpfCliente;
    private String cpfGarcom;
    private ArrayList<Integer> produtosPedidos = new ArrayList<Integer>();
    private ArrayList<Integer> qtdProdutosPedidos = new ArrayList<Integer>();
    private double precoTotal;
    private boolean pedidoPronto;
    private boolean pedidoPago;

    public Pedido(){
        precoTotal = 0.0;
    }
    
    /**
     * Método para adicionar produto ao pedido da mesa
     */
    public void addItemPedido(int codProduto, int quantidade) throws SQLException {
        if(Produto.verificaPossuiEstoque(codProduto, quantidade)){
            produtosPedidos.add(codProduto);
            qtdProdutosPedidos.add(quantidade);
            precoTotal = precoTotal + (Produto.buscaPreco(codProduto) * quantidade);

            for(int i = 0; i < quantidade; i++) {
                Produto.consomeProdutoEstoque(codProduto);
            }
        }
        else{
            System.out.printf("\nNao ha estoque para o produto %s\n!", Produto.buscaNome(codProduto));
        }
	}

	/**
     * Métodos de classe
     */
    public static void marcaProdutoPronto(int codPedido, int codProduto) throws SQLException{
		Connection con = new ConnectionFactory().getConexao();
        String sql = "UPDATE pedido_produto SET pronto = true " +
                            "WHERE cod_pedido = ? " +
                            "AND cod_produto = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, codPedido);
        stmt.setInt(2, codProduto);

        try {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
        }
    }

    
    public static void marcaPedidoPago(int codigo) throws SQLException{
		Connection con = new ConnectionFactory().getConexao();
        String sql = "UPDATE pedidos SET pedido_pago = true " +
                            "WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, codigo);

        try {
            stmt.executeUpdate();
            Mesa.desocupaMesa(buscaMesa(codigo));
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
        String sql = "INSERT INTO pedidos " +
                            "(cod_mesa, cpf_cliente, cpf_garcom, preco_total) " +
                            "VALUES(?, ?, ?, ?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, codigoMesa);
        stmt.setString(2, cpfCliente);
        stmt.setString(3, cpfGarcom);
        stmt.setDouble(4, precoTotal);

        try {
            stmt.executeUpdate();
            insereProdutosPedidos(produtosPedidos, qtdProdutosPedidos);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
    }


    public void insereProdutosPedidos(ArrayList<Integer> produtosPedidos, 
                                        ArrayList<Integer> qtdProdutosPedidos) 
                                        throws SQLException {

        Connection con = new ConnectionFactory().getConexao();
        int codPedido = buscaUltimoPedido();
        
        for(int i = 0; i < produtosPedidos.size(); i++) {
            String sql = "INSERT INTO pedido_produto (cod_pedido, cod_produto, qtd_produto) " +
                            "VALUES(?, ?, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, codPedido);
            stmt.setInt(2, produtosPedidos.get(i));
            stmt.setInt(3, qtdProdutosPedidos.get(i));

            try {
                stmt.executeUpdate();
            }
            catch(SQLException e) {
                throw new RuntimeException(e);
            }
            finally {
                stmt.close();
            }
        }
    }


    public int buscaUltimoPedido() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT codigo FROM pedidos ORDER BY codigo DESC " +
                        "FETCH FIRST 1 ROW ONLY;";
        PreparedStatement stmt = con.prepareStatement(sql);
        int codPedido = 0;

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                codPedido = rs.getInt("codigo");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
        }
        return codPedido;
    }


    public static double buscaConta(int codigo) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT preco_total FROM pedidos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        double conta = 0.0;

        stmt.setInt(1, codigo);

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                conta = rs.getDouble("preco_total");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return conta;
    }


    public static int buscaMesa(int codPedido) throws SQLException{
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT cod_mesa FROM pedidos WHERE codigo = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        int codMesa = 0;

        stmt.setInt(1, codPedido);

        try {
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                codMesa = rs.getInt("cod_mesa");
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        return codMesa;
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
    
    // Variavel cpfCliente
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
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
    
    // Variavel precoTotal
	public double getPrecoTotal() {
		return precoTotal;
	}

	// Variavel pedidoPronto
	public boolean isPedidoPronto() {
		return pedidoPronto;
	}
	public void setPedidoPronto(boolean pedidoPronto) {
		this.pedidoPronto = pedidoPronto;
	}

	// Variavel pedidoPago
	public boolean isPedidoPago() {
		return pedidoPago;
	}
    public void setPedidoPago(boolean pedidoPago) {
		this.pedidoPago = pedidoPago;
	}
}