<%-- 
    Document   : relatorioOcorrenciaAnimal
    Author     : tati
--%>

<%@page import="net.sf.jasperreports.engine.xml.JRXmlLoader"%>
<%@page import="net.sf.jasperreports.engine.design.JasperDesign"%>
<%@page import="java.net.URL"%>
<%@page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="net.sf.jasperreports.engine.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>

<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
<body>

<%
Connection conexao;
Class.forName("com.mysql.jdbc.Driver").newInstance();
conexao = DriverManager.getConnection("jdbc:mysql://localhost/siac", "root", "root");
//home/tati/Documentos/NetbeansProjects/NovoProjetoCio/SistemaCio/web/relatorios
//C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\regsensores\\regs.txt
//File reportFile = new File(application.getRealPath("C:/Users/JV/Documents/siac/web/relatorios/ocorrenciaAnimal.jasper"));
//URL url = this.getClass().getClassLoader().getResource("ocorrenciaAnimal.jasper");
//JasperReport relatorioJasper = (JasperReport)JRLoader.loadObject(url);

/*HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request); 
String fpath = srw.getRealPath("") + "ocorrenciaAnimal.jrxml"; 
JasperDesign jasperDesin = JRXmlLoader.load("ocorrenciaAnimal.jasper"); 
JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesin); */
Map parametros = new HashMap(); //parametros.put("Nome_parametro", "Valor_parametro");

//Map<String, String> parametros = new HashMap<String, String>();
//recuperacao de imagem para o relatorio
//passagem de parametro
//parametros.put("caminhoImagem", request.getSession().getServletContext().getRealPath("/images/relatorio/logo.jpg"));
 
//recuperacao do arquivo compilado do relatorio
InputStream relatorio = getClass().getResourceAsStream("/br/com/relatorio.jasper");
byte[] bytes = .pdfPrint(relatorio, parametros);

//byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parametros, conexao);
response.setContentType("application/pdf");
response.setContentLength(bytes.length);  
ServletOutputStream outputStream = response.getOutputStream();
outputStream.write(bytes, 0, bytes.length);
outputStream.flush();
outputStream.close();    
%> 

 </body>
</html>
