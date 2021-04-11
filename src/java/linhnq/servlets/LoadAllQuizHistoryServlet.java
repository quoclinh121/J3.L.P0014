/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.daos.TblQuizResultDAO;
import linhnq.dtos.TblQuizResultDTO;
import linhnq.dtos.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "LoadAllQuizHistoryServlet", urlPatterns = {"/LoadAllQuizHistoryServlet"})
public class LoadAllQuizHistoryServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoadAllQuizHistoryServlet.class);
    private final String STUDENT_PAGE = "student_page.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = STUDENT_PAGE;
        try {
            HttpSession session = request.getSession();
            TblUsersDTO user = (TblUsersDTO) session.getAttribute("LOGIN_USER");
            
            TblQuizResultDAO dao = new TblQuizResultDAO();
            List<TblQuizResultDTO> listQuizHistory = dao.loadQuizHistory("", user.getEmail());
            
            for (TblQuizResultDTO dto : listQuizHistory) {
                if(dto.getSubject().equals("Prj311- Java Desktop")) {
                    dto.setMark(Math.ceil((dto.getCorrect_answers() * 1.0 / 40) * 1000) / 100);
                } else {
                    dto.setMark(Math.ceil((dto.getCorrect_answers() * 1.0 / 50) * 1000) / 100);
                }
            }
            
            
            session.setAttribute("QUIZ_RESULT_HISTORY", listQuizHistory);
            
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
