/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import linhnq.daos.TblQuestionsDAO;
import linhnq.dtos.TblQuestionsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "UpdateQuestionServlet", urlPatterns = {"/UpdateQuestionServlet"})
public class UpdateQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateQuestionServlet.class);
    private final String SUCCESS = "SearchQuestionServlet";
    private final String ERROR = "update_page.jsp";

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

        String url = ERROR;
        try {
            String question_content = request.getParameter("txtQuestionContent").trim();
            String answerA = request.getParameter("txtAnswerA").trim();
            String answerB = request.getParameter("txtAnswerB").trim();
            String answerC = request.getParameter("txtAnswerC").trim();
            String answerD = request.getParameter("txtAnswerD").trim();
            String answer_content = null;
            String errors = "";

            if (question_content.length() > 8000) {
                errors += "Question content can't allow greater than 8000 characters";
            }
            if (!answerA.matches("[\\s\\w\\p{Punct}]+")) {
                errors += "Answer A can't allow special characters!<br/>";
            }
            if (!answerB.matches("[\\s\\w\\p{Punct}]+")) {
                errors += "Answer B can't allow special characters!<br/>";
            }
            if (!answerC.matches("[\\s\\w\\p{Punct}]+")) {
                errors += "Answer C can't allow special characters!<br/>";
            }
            if (!answerD.matches("[\\s\\w\\p{Punct}]+")) {
                errors += "Answer D can't allow special characters!<br/>";
            }
            
            if(answerA.equals(answerB) || answerA.equals(answerC) || answerA.equals(answerD) 
                    || answerB.equals(answerC) || answerB.equals(answerD) || answerC.equals(answerD)) {
                errors += "The answers are duplicated!<br/>";
            }

            if (errors.isEmpty()) {
                answer_content = answerA + "Ñ" + answerB + "Ñ" + answerC + "Ñ" + answerD;
                if (answer_content.length() > 8000) {
                    errors += "Total length of 4 answers can't allow greater than 7997 characters";
                }
            }

            if (!errors.isEmpty()) {
                request.setAttribute("ERRORS", errors);
            } else {
                String question_id = request.getParameter("questionID");
                String correct_answer = request.getParameter("cbxCorrectAnswer");
                String subjectName = request.getParameter("cbxSubject");
                String status = request.getParameter("cbxStatus:");

                if ("Answer A".equals(correct_answer)) {
                    correct_answer = answerA;
                } else if ("Answer B".equals(correct_answer)) {
                    correct_answer = answerB;
                } else if ("Answer C".equals(correct_answer)) {
                    correct_answer = answerC;
                } else {
                    correct_answer = answerD;
                }

                if ("Active".equals(status)) {
                    status = "1";
                } else {
                    status = "0";
                }

                TblQuestionsDTO dto = new TblQuestionsDTO(question_id, question_content, answer_content, correct_answer, null, subjectName, status);
                TblQuestionsDAO dao = new TblQuestionsDAO();
                if (dao.updateQuestion(dto)) {
                    url = SUCCESS;
                    request.setAttribute("UPDATE_SUCCESS", "Update question successful!");
                } else {
                    request.setAttribute("ERRORS", "Update failed");
                }
            }
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
