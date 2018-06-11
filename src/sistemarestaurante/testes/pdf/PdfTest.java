package sistemarestaurante.testes.pdf;

import sistemarestaurante.ferramentas.PdfFactory;
/**
 * PdfTest
 */
public class PdfTest {
    public static void main(String[] args) {
       PdfFactory.geraNF("NewInvoice.pdf", pedido, fornecedor, cliente);

    }
}