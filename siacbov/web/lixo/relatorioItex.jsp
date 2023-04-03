<%-- 
    Document   : relatorioItex
    Created on : 10/12/2014, 14:53:24
    Author     : tati
--%>

<%@page import="com.sun.jndi.ldap.Connection"%>
<%@page import="br.edu.cio.util.Conexao"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="com.itextpdf.text.BaseColor"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="java.io.IOException"%>
<%@page import="com.itextpdf.text.DocumentException"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.PageSize"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
       //criamos um documento vazio
        Document documentoPDF = new Document();
        
        try{
            
            //cria uma instancia do documento e da o nome do pdf
            // /home/tati/Documentos/NetbeansProjects/NovoProjetoCio/SistemaCio/web/relatorios/relatorioPDF.pdf
           // D:\\PINHAS.pdfDF_PROGRAM
            PdfWriter.getInstance(documentoPDF, new FileOutputStream("C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\web\\relatorios\\relatorioPDF.pdf"));
            
            //abrir o documento
            documentoPDF.open();
            
            //setar o tamnho da página
            documentoPDF.setPageSize(PageSize.A4);
            
            //adicinando primeiro paragrafo
            documentoPDF.add(new Paragraph("GERANDO PDF "));
            
            //nova pagina
            //documentoPDF.newPage();
            
            //paragrafo da segunda pagina
           //documentoPDF.add(new Paragraph("Parágrafo de teste da segunda página"));
            
            //imagem do relatorio
            // D:\\imagens\\minhas\\brpig.jpg
            Image imagem = Image.getInstance("/home/tati/Documentos/NetbeansProjects/NovoProjetoCio/SistemaCio/web/imagens/vacas-leiteiras.jpg");
            
            //setar o tamanho da imagem
            //imagem.scaleToFit(400, 200);
            
            //adicionar a imagem ao pdf
            documentoPDF.add(imagem);
            
            
                PdfPTable tabela = new PdfPTable(8);
                PdfPCell cabecalho = new PdfPCell(new Paragraph("Lista de Ocorrência Animal"));
                cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
                cabecalho.setBorder(PdfPCell.NO_BORDER);
                //cabecalho.setBackgroundColor(BaseColor.RED);
                cabecalho.setBackgroundColor(new BaseColor(100,150,200));
                cabecalho.setColspan(8);
                tabela.addCell(cabecalho);
                tabela.addCell("Código");
                tabela.addCell("Data");
                tabela.addCell("Tipo");
                tabela.addCell("Descrição");
                tabela.addCell("Observação");
                tabela.addCell("Responsável");
                tabela.addCell("Código Referência");
                tabela.addCell("Animal");
                                
                Connection ocorrencia_animal = new Conexao();
                ocorrencia_animal.conecta();
                statement = ocorrencia_animal.conexao.createStatement();
                ocorrencia_animal.executeSQL("select * from ocorrencia_animal");
                while(ocorrencia_animal.resultset.next()){
                    tabela.addCell(ocorrencia_animal.resultset.getString("Codigo"));
                    tabela.addCell(ocorrencia_animal.resultset.getString("Data"));
                    tabela.addCell(ocorrencia_animal.resultset.getString("Tipo"));
                    tabela.addCell(ocorrencia_animal.resultset.getString("Descricao"));
                }
                
                
                documentoPDF.add(tabela);
            
            
            
            
        }catch(DocumentException de){
            de.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            documentoPDF.close();
        }
    }

%>
   


