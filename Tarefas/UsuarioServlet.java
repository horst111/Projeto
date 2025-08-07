import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/cadastrar", "/entrar", "/sair" })
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
				conectarUsuario(request, response);
				break;

			case "/sair":
				desconectarUsuario(request, response);
				break;

			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}

	}

	private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String apelido = request.getParameter("apelido");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		dao.inserirUsuario(new Usuario(nome, sobrenome, apelido, email, senha));
		response.sendRedirect("/entrar");
	}

	private void conectarUsuario(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, SQLException {
			
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
	
		Usuario usuario = dao.validarConexao(email, senha);
				
		if(usuario != null) {
		HttpSession session = request.getSession();
		session.setAttribute("usuarioLogado", usuario);
		response.sendRedirect("/");
		}
			
		else {
		request.setAttribute("erro", "Email ou senha incorretos");
		request.getRequestDispatcher("/login").forward(request, response); 
		}
	
	}

	private void desconectarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
	
		response.sendRedirect("/login");
	}

}
