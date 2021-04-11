/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "GetQuestionsBankServlet", urlPatterns = {"/GetQuestionsBankServlet"})
public class GetQuestionsBankServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetQuestionsBankServlet.class);
    private final String STUDENT_PROCESS = "ChangeQuestionServlet";

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

        String url = STUDENT_PROCESS;
        try {
            String subject = request.getParameter("cbxSubject");
            int questionRemaining = 0;
            int time = 0;
            int key = 0;
            Map<Integer, TblQuestionsDTO> questionBank = new HashMap<>();
            Map<Integer, String> answerList = new HashMap<>();
            List<TblQuestionsDTO> questionList = null;
            HttpSession session = request.getSession();

            if ("Prj311- Java Desktop".equals(subject)) {
                questionRemaining = 40;
                time = 60 * 60;
                for (int i = 0; i < 40; i++) {
                    answerList.put(i, null);
                }
                session.setAttribute("SUBJECT_QUIZ", "Prj311- Java Desktop");
            } else {
                questionRemaining = 50;
                time = 80 * 60;
                for (int i = 0; i < 50; i++) {
                    answerList.put(i, null);
                }
                session.setAttribute("SUBJECT_QUIZ", "Prj321- Java Web");
            }

            TblQuestionsDAO dao = new TblQuestionsDAO();
            do {
                questionList = dao.getRandomQuestions(questionRemaining, subject);

                for (int i = 0; i < questionList.size(); i++) {
                    questionBank.put(key, questionList.get(i));
                    key++;
                }   
                questionRemaining -= questionList.size();
            } while (questionRemaining != 0);

            session.setAttribute("QUESTION_BANK", questionBank);
            session.setAttribute("STUDENT_ANSWER_LIST", answerList);
            session.setAttribute("TIME", time);

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
