package controller;

import dao.ContactDAO;
import model.Contact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet xử lý form liên hệ
 *
 *  GET  /contact  -> hiển thị form
 *  POST /contact  -> lưu vào bảng contacts
 */
@WebServlet(name = "LienHeServlet", urlPatterns = {"/contact"})
public class LienHeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/LienHe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name    = request.getParameter("name");
        String email   = request.getParameter("email");
        String phone   = request.getParameter("phone");
        String message = request.getParameter("message");

        if (isBlank(name) || isBlank(email) || isBlank(message)) {
            request.setAttribute("error", "Vui lòng điền đầy đủ Họ tên, Email và Nội dung.");
            request.getRequestDispatcher("/LienHe.jsp").forward(request, response);
            return;
        }

        Contact contact = new Contact(name.trim(), email.trim(),
                                      phone == null ? "" : phone.trim(),
                                      message.trim());

        boolean ok = ContactDAO.save(contact);

        if (ok) {
            request.setAttribute("success", "Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi sớm nhất.");
        } else {
            request.setAttribute("error", "Gửi thất bại, vui lòng thử lại.");
        }
        request.getRequestDispatcher("/LienHe.jsp").forward(request, response);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
