package sistemarestaurante.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import sistemarestaurante.estoque.Produto;
import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.individuos.Cliente;
import sistemarestaurante.servico.Mesa;
import sistemarestaurante.servico.Pagamento;
import sistemarestaurante.servico.Pedido;

public class Garcom {
    public static void menuPrincipal(String cpfUsuario) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        int opcao = -1;

        while(opcao != 0){
            System.out.println();
            System.out.println();
            System.out.println("[1] Recepcionar proximo cliente.");
            System.out.println("[2] Realizar novo pedido.");
            System.out.println("[3] Consultar pedidos prontos.");
            System.out.println("[4] Receber pagamento de pedido.");
            System.out.println("[0] Sair.");
            System.out.println();
    
            System.out.print("Digite a opção desejada: ");
            opcao = Integer.parseInt(input.nextLine());

            switch(opcao){
                case 1:
                    recepcionaCliente();
                    break;

                case 2:
                    realizaPedido(cpfUsuario);
                    break;
                
                case 3:
                    consultaPedidos(cpfUsuario);
                    break;
                    
                case 4:
                    recebePagamento(cpfUsuario);
                    break;

                case 0:
                    return;
                
                default:
                    System.out.println("Opcao invalida!");
            }
        }
        //input.close();
    }

    public static void recepcionaCliente() throws SQLException, ParseException {
        Cliente cliente = new Cliente();
        Scanner input = new Scanner(System.in);
        String cpf = null;

        System.out.print("Insira o CPF do cliente: ");
        cpf = input.nextLine();

        if(cpf.length() < 1) {
        }
        else if(! Cliente.verifCadastroExiste(cpf)) {
            cadastraCliente(cpf); 
        }
        //input.close();
    }

    
    public static void cadastraCliente(String cpf) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        Cliente cliente = new Cliente();

        cliente.setCpf(cpf);

        System.out.println("Cliente nao cadastrado. Insira abaixo as informacoes do cliente.");

        System.out.print("Nome: ");
        cliente.setNome(input.nextLine());
        
        System.out.print("RG: ");
        cliente.setRg(input.nextLine());
        
        System.out.print("Data de nascimento [DD/MM/AAAA]: ");
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            cliente.setDataNascimento( (java.util.Date)formatter.parse(input.nextLine()));
        } 
        catch(ParseException e) {            
            throw e;
        }
        
        System.out.print("Nome do pai: ");
        cliente.setFiliacaoPai(input.nextLine());
        
        System.out.print("Nome da mae: ");
        cliente.setFiliacaoMae(input.nextLine());
        
        System.out.print("Naturalidade: ");
        cliente.setNaturalidade(input.nextLine());
        
        System.out.print("Estado civil: ");
        cliente.setEstadoCivil(input.nextLine());
        
        System.out.print("Sexo [1:Masculino | 2:Feminino]: ");
        if(Integer.parseInt(input.nextLine()) == 1){
            cliente.setSexoMasculino(true);
        }
        else{
            cliente.setSexoMasculino(false);
        }

        System.out.print("Telefone: ");
        cliente.setTelefone(input.nextLine());
        
        System.out.print("E-Mail: ");
        cliente.setEmail(input.nextLine());
        
        System.out.print("Endereco: ");
        cliente.setEndereco(input.nextLine());
        
        System.out.print("Escolaridade: ");
        cliente.setEscolaridade(input.nextLine());
        
        System.out.print("Profissao: ");
        cliente.setProfissao(input.nextLine());

        cliente.insereBanco();
        System.out.printf("\nO cliente %s foi cadastrado no sistema.\n", cliente.getNome());
        //input.close();
    }

    public static void realizaPedido(String cpfGarcom) throws SQLException{
        Scanner input = new Scanner(System.in);
        Pedido pedido = new Pedido();
        int codMesa;
        int codigoProduto;
        int qtdProduto;
        boolean pedidoConcluido = false;
        
        pedido.setCpfGarcom(cpfGarcom);

        while(pedido.getCodigoMesa() < 1) {
            System.out.print("Insira o numero da mesa: ");
            codMesa = Integer.parseInt(input.nextLine());
            
            if(Mesa.verifMesaOcupada(codMesa)) {
                System.out.printf("A mesa %d esta ocupada.\n", codMesa);
            }
            else {
                pedido.setCodigoMesa(codMesa);
            }
        }

        System.out.print("Insira o CPF do cliente: ");
        pedido.setCpfCliente(input.nextLine());
        Produto.imprimeProdutos();
        
        while(!pedidoConcluido) {
            System.out.print("Digite o codigo do produto ou \"0\" para sair: ");
            codigoProduto = Integer.parseInt(input.nextLine());

            if(codigoProduto > 0){
                System.out.printf("Digite a quantidade do produto %s: ", Produto.buscaNome(codigoProduto));
                qtdProduto = Integer.parseInt(input.nextLine());

                pedido.addItemPedido(codigoProduto, qtdProduto);
            }
            else{
                pedidoConcluido = true;
            }
        }

        pedido.insereBanco();
        //input.close();
    }


    public static void consultaPedidos(String cpfGarcom) throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
        String sql = "SELECT p.codigo, p.cod_mesa , p.preco_total " +
                            "FROM pedidos AS p " +
                            "INNER JOIN (SELECT cod_pedido FROM pedido_produto " +
                                        "WHERE cod_pedido " +
                                        "NOT IN (SELECT cod_pedido FROM pedido_produto " +
                                                    "WHERE pronto = false)) AS pronto " +
                            "ON p.codigo = pronto.cod_pedido " +
                        "WHERE p.cpf_garcom = ? AND pedido_pago = false " +
                        "GROUP BY codigo ORDER BY codigo;";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, cpfGarcom);

        try {
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n\n|Numero do pedido - Numero da mesa - Preco|\n");

            while(rs.next()){
                int codigoPedido = rs.getInt("codigo");
                int codigoMesa = rs.getInt("cod_mesa");
                double precoTotal = rs.getDouble("preco_total");
                
                System.out.printf("|%d\t\t- %d\t\t- %.2f|\n", codigoPedido, codigoMesa, precoTotal);
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

    
    public static void recebePagamento(String cpfGarcom) throws SQLException throws IOException {
        Scanner input = new Scanner(System.in);
        int codigoPedido;
        double resultado=0;
        String nome;
        int qtd;
        double preco;
        FileWriter arq = new FileWriter("C:\\Users\\Public\\Documents\\notaFiscal.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("Comprovante de pagamento\n O Comilão:\n");
        System.out.print("Digite o número do pedido: ");
        codigoPedido = Integer.parseInt(input.nextLine());
        
        
        SELECT pe.codigo AS cod_pedido, 
        pp_pr.nome AS produto, 
        pp_pr.qtd_produto AS quantidade, 
        pp_pr.preco AS preco_unitario ,
        (pp_pr.qtd_produto * pp_pr.preco) AS preco_total
         FROM pedidos AS pe
        INNER JOIN(SELECT pp.cod_pedido, pp.cod_produto, pp.qtd_produto, pr.nome, pr.preco 
        FROM pedido_produto AS pp 
        INNER JOIN produtos AS pr 
        ON pp.cod_produto = pr.codigo) AS pp_pr
        ON pe.codigo = pp_pr.cod_pedido
        setInt(1, codigoPedido)
        WHERE pe.codigo = '?'
        ORDER BY produto;
        Pagamento.insereBanco(codigoPedido);
        Pedido.marcaPedidoPago(codigoPedido);
          try {
            ResultSet rs = stmt.executeQuery();
              while(rs.next()){
                   nome= rs.getString("produto");
                   qtd=rs.getInt("quantidade");
                   preco=rs.getDouble("preco_total");
                   gravarArq.printf("%i x %s =%f", qtd,nome,preco);
                   resultado=resultado+preco;
              }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
        
        
        gravarArq.printf("total:%f\n Volte Sempre", resultado);
        arq.close();
        System.out.printf("\nFoi recebido R$ %.2f do pedido de número %d.\n\n", 
                            Pedido.buscaConta(codigoPedido), codigoPedido);

        //input.close();
    }
}
