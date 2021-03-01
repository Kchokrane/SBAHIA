package web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.LoginDao;
import org.apache.catalina.connector.Response;

import dao.IProduitDao;
import dao.ProduitDaoImpl;
import metier.entities.Produit;
import metier.entities.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;


@WebServlet({"/","/home",})
public class ServletController extends HttpServlet {

	private static final int serialVersionUID = (int) 1L;
	
	private IProduitDao produitDao;
	private LoginDao Login;

	public void init(ServletConfig config) throws ServletException {
         Login = new LoginDao();
		produitDao = new ProduitDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path=request.getServletPath();
		
		if(path.equals("/login")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String role = request.getParameter("role");


			User user = Login.checkLogin(email, password, role);
			System.out.println(user.toString());
			if(!user.equals(null)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", user.getUsername());
				response.sendRedirect("chercher?motCle=");
			}
			else {
				response.sendRedirect("login.jsp");
			}
			
		}

		else if(path.contentEquals("/logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("username");
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
		else if(path.equals("/home") || path.equals("/")) {
			request.getRequestDispatcher("produits.jsp").forward(request, response);
		}

		else if(path.equals("/chercher")) {
			String motCle = request.getParameter("motCle");
			ProduitModel model = new ProduitModel();
			model.setMotCle(motCle);
			List<Produit> listProduit = produitDao.produitsParMotCle("%"+motCle+"%");
			model.setListProduit(listProduit);
			request.setAttribute("model", model);
			request.getRequestDispatcher("produits.jsp").forward(request, response);
		}
		else if(path.equals("/creer")) {
			request.setAttribute("produit", new Produit()); // initier les valeurs par 0 ou null
			request.getRequestDispatcher("ajouterProduit.jsp").forward(request, response);
		}
		else if(path.equals("/enregister")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String designation = request.getParameter("designation");
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			double prix = Double.parseDouble(request.getParameter("prix"));
			InputStream inputStream = null;
			//Part filePart = request.getPart("image");
			//if (filePart != null) {
				//inputStream = filePart.getInputStream();
			//

			//byte[] Image = IOUtils.toByteArray(inputStream);
			Produit produit = produitDao.saveProduit(new Produit(id,designation,quantite,prix));
			request.setAttribute("produit", produit);
			request.getRequestDispatcher("produit-info.jsp").forward(request, response);
		}
		else if(path.equals("/supprimer")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Produit produit = produitDao.getProduit(id);
			request.setAttribute("produit", produit);
			request.getRequestDispatcher("supprimerProduit.jsp").forward(request, response);	
		}
		
		else if(path.equals("/confirmer-suppression")) {
			int id = Integer.parseInt(request.getParameter("id"));
			produitDao.deleteProduit(id);
			response.sendRedirect("chercher?motCle=");
		}
		else if(path.contentEquals("/modifier")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Produit produit = produitDao.getProduit(id);
			request.setAttribute("produit", produit);
			request.getRequestDispatcher("modifierProduit.jsp").forward(request, response);	

		}
		else if(path.equals("/valider-modification")) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			String designation = request.getParameter("designation");
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			double prix = Double.parseDouble(request.getParameter("prix"));
		
			Produit produit = new Produit(id,designation,quantite,prix);

			produit.setId(id);

			produitDao.updateProduit(produit);

			response.sendRedirect("chercher?motCle=");
		}
		else {
			response.sendError(Response.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
