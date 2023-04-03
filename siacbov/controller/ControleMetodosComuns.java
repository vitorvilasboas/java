
package br.edu.cio.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControleMetodosComuns {
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
