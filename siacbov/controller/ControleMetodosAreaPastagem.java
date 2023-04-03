package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.util.DAOFactory;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.*;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Level;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import static jdk.nashorn.internal.objects.NativeError.getFileName;

@MultipartConfig
public class ControleMetodosAreaPastagem implements ControleMetodosComuns {
        
    AreaPastagemDAO acessoBD = DAOFactory.gerarAreaPastagemDAO();
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    
    private String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
    for (String content : part.getHeader("content-disposition").split(";")) {
        if (content.trim().startsWith("filename")) {
            return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return null;
}
     
    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AreaPastagem ap = new AreaPastagem();
        
        ap.setNome(request.getParameter("cmp_pasto_nome"));
        ap.setArea(Double.parseDouble(request.getParameter("cmp_pasto_area")));
        ap.setObservacao(request.getParameter("cmp_pasto_observacao"));
        ap.setImagem("/siac/imagens/pastos/" + request.getParameter("cmp_pasto_imagem"));
        ap.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_pasto_propriedade"))));
        
        /*int retorno;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
        if (isMultipart) {  
            try {
                FileItemFactory factory = new DiskFileItemFactory();  
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest((RequestContext) request);
                //List<FileItem> items = (List<FileItem>) upload.parseRequest(request);  
                for (FileItem item : items) {  
                    if (item.isFormField()) {//se for um campo normal de form  
                        if ("cmp_pasto_nome".equals(item.getFieldName())) 
                            ap.setNome(item.getString());   
                        if ("cmp_pasto_area".equals(item.getFieldName()))
                            ap.setArea(Double.parseDouble(item.getString()));   
                        if ("cmp_pasto_observacao".equals(item.getFieldName())) 
                            ap.setObservacao(item.getString());  
                        if ("cmp_pasto_imagem".equals(item.getFieldName())) 
                            ap.setImagem("/siac/imagens/pastos/" + item.getString());
                        if ("cmp_pasto_propriedade".equals(item.getFieldName())) 
                            ap.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(item.getString())));
                        //response.getWriter().println("Cheguei ate aqui!" + ap.getNome());  
                    } else {   
                        File uploadedFile = new File("C:/Users/JV/Documents/NetBeansProjects/siac/web/imagens/pastos/" + new Date().getTime() + "_" + item.getName());  
                        item.write(uploadedFile);  
                    }  
                }  
            } catch (Exception e) {  
                response.getWriter().println("ocorreu um problema ao fazer o upload: " + e.getMessage());  
            }*/
            /*
            if(ServletFileUpload.isMultipartContent(request)){
                try {
                    List<FileItem> multiparts = new ServletFileUpload( new DiskFileItemFactory() ).parseRequest((RequestContext) request);
                    for(FileItem item : multiparts){
                        if(!item.isFormField()){
                            String name = new File(item.getName()).getName();
                            item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                        }
                    }
                   File uploaded successfully
                   request.setAttribute("messagem", "File Uploaded Successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "erro");
                   request.setAttribute("messagem", "File Upload Failed due to " + ex);
                }          
            }else{
                request.setAttribute("messagem", "Sorry this Servlet only handles file upload request");
            }
            request.getRequestDispatcher("/result.jsp").forward(request, response);*/

 
        try {
            acessoBD.cadastrarDAO(ap);
            
            
            request.setAttribute("mensagem", "Área de Pastagem cadastrada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Área de Pastagem não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        AreaPastagem pasto = acessoBD.buscarPorIdDAO(id);
        request.setAttribute("pasto", pasto);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=areaPastagem").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        AreaPastagem ap = new AreaPastagem();
        ap.setId(id);
        ap.setCodigo(request.getParameter("cmp_edt_pasto_codigo"));
        ap.setNome(request.getParameter("cmp_edt_pasto_nome"));
        ap.setArea(Double.parseDouble(request.getParameter("cmp_edt_pasto_area")));
        ap.setObservacao(request.getParameter("cmp_edt_pasto_observacao"));
        ap.setImagem("imagens/pastos/" + request.getParameter("cmp_edt_pasto_imagem"));
        ap.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_edt_pasto_propriedade"))));
        try {
            acessoBD.editarDAO(ap);
            ap = acessoBD.buscarPorIdDAO(id);
            request.setAttribute("pasto", ap);
            request.setAttribute("mensagem", "Área de Pastagem atualizada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Área de Pastagem  não atualizada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição       

        if (acessoBDLote.listarPorPropriedadeDAO(id) != null) {
            request.setAttribute("mensagem", " Área de Pastagem NÃO pode ser excluida. Existem lotes vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);
        } else if (acessoBDAnimal.listarPorPropriedadeDAO(id) != null) {
            request.setAttribute("mensagem", "Área de Pastagem NÃO pode ser excluida. Existem animais vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);
        } else if (!acessoBD.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Área de Pastagem.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);

        } else {
            request.setAttribute("mensagem", "Área de Pastagem excluida com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);
        }
    }



    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_buscar_pasto");//recebe o dado informado no formLogin do index
        List<AreaPastagem> resultado = acessoBD.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("pastosEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=areaPastagem").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Área de Pastagem não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        }
    }

 

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AreaPastagem resultado = acessoBD.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if ( resultado != null ) {
            request.setAttribute("detalhespasto", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=areaPastagem").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Pasto não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=areaPastagem").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
