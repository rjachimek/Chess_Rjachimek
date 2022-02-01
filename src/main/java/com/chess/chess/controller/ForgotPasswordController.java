package com.chess.chess.controller;

import com.chess.chess.model.User;
import com.chess.chess.service.UserDetailsServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDetailsServiceImpl resetServices;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            resetServices.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Mail został wysłany na podany mail");

        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Błąd podczas wysyłania");
        }

        return "forgot_password";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("chess.radek@gmail.com", "Support");
        helper.setTo(recipientEmail);

        String subject = "Reset hasła Amatorski turniej szachowy";

        String content = "<p>Hello,</p>"
                + "<p>Kiliknij w link poniżej aby zmienić hasło:</p>"
                + "<p><a href=\"" + link + "\">Zmień hasło</a></p>"
                + "<br>"
                + "<p>Zignoruj ten mail jeżeli nie prosiłes o przypomnienie hasła</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = resetServices.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "login4";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = resetServices.getByResetPasswordToken(token);
        model.addAttribute("title", "Zresetuj hasło");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            resetServices.updatePassword(user, password);

            model.addAttribute("message", "Pomyślnie zmieniłeś hasło.");
        }

        return "login4";
    }
}