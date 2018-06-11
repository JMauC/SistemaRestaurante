package sistemarestaurante.ferramentas;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import sistemarestaurante.administrativo.Fornecedor;
import sistemarestaurante.administrativo.Restaurante;
import sistemarestaurante.individuos.Cliente;
import sistemarestaurante.servico.Pedido;



/**
 * PdfFactory
 */
public class PdfFactory {
        public static void geraNF(String destino, Pedido pedido, Fornecedor fornecedor, Cliente cliente) {
                PdfWriter writer = new PdfWriter(destino);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                addTitle(document);
                addDadosDoRestaurante(document);

                
        }

        public static void addTitle(Document document){
                document.add(new Paragraph("DANFE").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER));
        }
        
        public static void addDadosDoRestaurante(Document document)
        {
                document.add(new Paragraph("Comprador: "+Restaurante.razaoSocial).setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(0.2f));
                document.add(new Paragraph("Endereço: "+Restaurante.endereco).setMultipliedLeading(.2f));
                document.add(new Paragraph("Telefone: "+Restaurante.telefone).setMultipliedLeading(.2f));
        }


}