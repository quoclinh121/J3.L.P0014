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
import linhnq.daos.TblQuestionsDAO;
import linhnq.dtos.TblQuestionsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "SearchQuestionServlet", urlPatterns = {"/SearchQuestionServlet"})
public class SearchQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchQuestionServlet.class);
    private final String ADMIN_PAGE = "admin_page.jsp";

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

        String url = ADMIN_PAGE;
        try {
            HttpSession session = request.getSession();

            String subject = null;
            String status = null;
            String searchValue = null;

            int pageSize = 5;
            int pageNumber = 1;

            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            }

            if ("true".equals(request.getParameter("isClickSearch"))) {
                subject = request.getParameter("cbxSubject");
                status = request.getParameter("cbxStatus");
                searchValue = request.getParameter("txtSearchValue");
                session.setAttribute("SUBJECT", subject);
                session.setAttribute("STATUS", status);
                session.setAttribute("SEARCH_VALUE", searchValue);
            } else {
                subject = (String) session.getAttribute("SUBJECT");
                status = (String) session.getAttribute("STATUS");
                searchValue = (String) session.getAttribute("SEARCH_VALUE");
            }

            if ("All Subject".equals(subject)) {
                subject = "";
            }
            if ("All Status".equals(status)) {
                status = "";
            } else if ("Active".equals(status)) {
                status = "1";
            } else {
                status = "0";
            }

            TblQuestionsDAO dao = new TblQuestionsDAO();

            List<TblQuestionsDTO> listQuestion = dao.searchQuestion(searchValue, subject, status, pageNumber, pageSize);

            if (listQuestion != null) {
                int noOfRecord = dao.getNoOfRecord(searchValue, subject, status);
                int noOfPage = (int) Math.ceil(noOfRecord * 1.0 / pageSize);
                request.setAttribute("NO_OF_PAGE", noOfPage);
                request.setAttribute("CURRENT_PAGE", pageNumber);
            }
            session.setAttribute("QUESTION_LIST", listQuestion);

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
