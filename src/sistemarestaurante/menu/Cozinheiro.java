package sistemarestaurante.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.servico.Pedido;

public class Cozinheiro {
    public static void menuPrincipal(String cpfUsuario) throws SQLException {
        Scanner input = new Scanner(System.in);
        int opcao = -1;

        while(opcao != 0){
            System.out.println();
            System.out.println();
            System.out.println("[1] Consultar pedidos pendentes.");
            System.out.println("[2] Registrar pedido pronto.");
            System.out.println("[3] Consultar estoque.");
            System.out.println("[0] Sair.");
            System.out.println();
    
            System.out.print("Digite a opcao desejada: ");
            opcao = Integer.parseInt(input.nextLine());

            switch(opcao){
                case 1:
                    consultaPendentes();
                    break;

                case 2:
                    registraPreparo();
                    break;
                
                case 3:
                    consultaEstoque();
                    break;
                    
                case 0:
                    return;
                
                default:
                    System.out.println("Opcao invalida!");
            }
        }
        //input.close();
    }


    public static void consultaPendentes() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT pp.cod_pedido, pr.nome, pp.qtd_produto " +
                        "FROM produtos AS pr " +
                        "INNER JOIN " +
                            "(SELECT cod_pedido, cod_produto, qtd_produto " + 
                            "FROM pedido_produto WHERE pronto = false) AS pp " +
                        "ON pr.codigo = pp.cod_produto " +
                        "WHERE bebida = false " +
                        "ORDER BY pp.cod_pedido;";
        PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|Numero do pedido - Produto\t\t- Quantidade|\n");

            while(rs.next()){
                int codPedido = rs.getInt("cod_pedido");
                String produto = rs.getString("nome");
                int qtdProduto = rs.getInt("qtd_produto");
                
                System.out.printf("|%d\t\t- %s\t\t- %d\t\t|\n", codPedido, produto, qtdProduto);
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


    public static void registraPreparo() throws SQLException {
        Scanner input = new Scanner(System.in);
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT pp.cod_pedido, pp.cod_produto, pr.nome, pp.qtd_produto " +
                        "FROM produtos AS pr " +
                        "INNER JOIN " +
                            "(SELECT cod_pedido, cod_produto, qtd_produto " + 
                            "FROM pedido_produto WHERE pronto = false) AS pp " +
                        "ON pr.codigo = pp.cod_produto " +
                        "WHERE pr.bebida = false " +
                        "AND pp.cod_pedido = ? " +
                        "ORDER BY pp.cod_pedido;";
        PreparedStatement stmt = con.prepareStatement(sql);

        System.out.printf("\nInsira o numero do pedido preparado: ");
        stmt.setInt(1, Integer.parseInt(input.nextLine()));
        System.out.println();

        try {
            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                int codPedido = rs.getInt("cod_pedido");
                int codProduto = rs.getInt("cod_produto");
                String produto = rs.getString("nome");
                int qtdProduto = rs.getInt("qtd_produto");

                Pedido.marcaProdutoPronto(codPedido, codProduto);

                System.out.printf("\nFoi registrado o preparo de %d unidade(s) do produto " +
                                    "%s referentes ao pedido numero %d.\n", 
                                    qtdProduto, produto, codPedido);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        //input.close();
    }


    public static void consultaEstoque() {
        System.out.println("Opcao nao implementada!");
    }
}