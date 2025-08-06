import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/cadastrar", "/entrar", "/sair"})
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UsuarioDAO dao;


	public void init() {
		dao = new UsuarioDAOImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		try {
			
			switch (action) {
			
			case "/cadastrar": 
				cadastrarUsuario(request, response);
				break;
			
			case "/entrar":
			    // conectarUsuario(request, response);
			    break;

			case "/sair":
			    // desconectarUsuario(request, response);
			    break;
			
			}
		
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}

	}
	
	private void cadastrarUsuario(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String apelido = request.getParameter("apelido");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String descricao = request.getParameter("descricao");
		dao.inserirUsuario(new Usuario(nome, sobrenome, apelido, email, senha, descricao));
		response.sendRedirect("/login");
	}

	private void conectarUsuario(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, SQLException {
	
	}

	private void desconectarUsuario(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	
	}






}
