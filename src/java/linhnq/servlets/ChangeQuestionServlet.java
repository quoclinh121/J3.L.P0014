/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.dtos.TblQuestionsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "ChangeQuestionServlet", urlPatterns = {"/ChangeQuestionServlet"})
public class ChangeQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ChangeQuestionServlet.class);
    private final String DO_QUIZ = "do_quiz_page.jsp";
    private final String SUBMIT = "SubmitQuizServlet";

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

        String url = DO_QUIZ;
        try {
            int questionNumber = 0;
            int currentQuestion = 0;
            String answer = null;

            HttpSession session = request.getSession();
            Map<Integer, String> answerList = (Map<Integer, String>) session.getAttribute("STUDENT_ANSWER_LIST");
            Map<Integer, TblQuestionsDTO> questionBank = (Map<Integer, TblQuestionsDTO>) session.getAttribute("QUESTION_BANK");

            if (request.getParameter("answer") != null) {
                answer = request.getParameter("answer");
                currentQuestion = Integer.parseInt(request.getParameter("currentQuestion"));
                answerList.put(currentQuestion, answer);
            }

            if (request.getParameter("btnSubmit") != null) {
                url = SUBMIT;
            } else {
                if (request.getParameter("chooseQuestionNumber") != null) {
                    questionNumber = Integer.parseInt(request.getParameter("chooseQuestionNumber")) - 1;
                } else if (request.getParameter("questionNumber") != null) {
                    questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
                }

                if (request.getParameter("timeRemain") != null) {
                    int timeRemain = Integer.parseInt(request.getParameter("timeRemain"));
                    session.setAttribute("TIME", timeRemain);
                }

                TblQuestionsDTO question = questionBank.get(questionNumber);
                session.setAttribute("QUESTION", question);
                request.setAttribute("CURRENT_QUESTION", questionNumber);
            }
            
            session.setAttribute("STUDENT_ANSWER_LIST", answerList);
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
