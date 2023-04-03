<%-- 
    Document   : testeReport
    Created on : 09/12/2014, 20:08:32
    Author     : tati
--%>

<%@page import="java.sql.Statement"%>
<%@page import="br.edu.cio.util.Conexao"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.itextpdf.text.BaseColor"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="com.itextpdf.text.Font"%>
<%@page import="java.io.OutputStream"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.PageSize"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="java.io.IOException"%>
<%@page import="com.itextpdf.text.DocumentException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatório Teste com iText</title>
    </head>
    <body>
        <h1>Relatório iText</h1>
        
        <%           
        //("/home/tati/Documentos/NetbeansProjects/NovoProjetoCio/SistemaCio/web/relatorios/relatorioPDF.pdf")
        %>

    </body>
</html>
