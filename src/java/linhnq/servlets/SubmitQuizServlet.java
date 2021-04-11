/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.daos.TblQuizResultDAO;
import linhnq.daos.TblResultDetailDAO;
import linhnq.dtos.TblQuestionsDTO;
import linhnq.dtos.TblQuizResultDTO;
import linhnq.dtos.TblUsersDTO;
import linhnq.utils.RandomID;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/SubmitQuizServlet"})
public class SubmitQuizServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SubmitQuizServlet.class);
    private final String DO_QUIZ_PAGE = "do_quiz_page.jsp";
    private final String ERROR = "LogoutServlet";

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
            double mark = 0;
            int correct_answers = 0;
            HttpSession session = request.getSession();
            TblUsersDTO user = (TblUsersDTO) session.getAttribute("LOGIN_USER");
            Map<Integer, String> answerList = (Map<Integer, String>) session.getAttribute("STUDENT_ANSWER_LIST");
            Map<Integer, TblQuestionsDTO> questionBank = (Map<Integer, TblQuestionsDTO>) session.getAttribute("QUESTION_BANK");

            if (!answerList.isEmpty() && !questionBank.isEmpty()) {
                Date dueDate = new Date();
                String resultID = RandomID.randomString(50);
                String subject = (String) session.getAttribute("SUBJECT_QUIZ");
                TblQuizResultDTO dto = new TblQuizResultDTO(resultID, subject, dueDate, user.getEmail(), 0, 0);

                TblQuizResultDAO quizResultDAO = new TblQuizResultDAO();
                TblResultDetailDAO detailDAO = new TblResultDetailDAO();

                if (quizResultDAO.createNewResult(dto)) {
                    for (int i = 0; i < answerList.size(); i++) {
                        if (questionBank.get(i).getAnswer_correct().equals(answerList.get(i))) {
                            detailDAO.storedQuestion(resultID, questionBank.get(i).getQuestion_id(), true);
                            correct_answers++;
                        } else {
                            detailDAO.storedQuestion(resultID, questionBank.get(i).getQuestion_id(), false);
                        }
                    }

                    if (correct_answers != 0) {
                        mark = Math.ceil((correct_answers * 1.0 / answerList.size()) * 1000) / 100;
                    }

                    dto.setCorrect_answers(correct_answers);
                    dto.setMark(mark);
                    request.setAttribute("QUIZ_RESULT", dto);
                }
                session.removeAttribute("STUDENT_ANSWER_LIST");
                session.removeAttribute("QUESTION_BANK");
                url = DO_QUIZ_PAGE;
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
