
package vn.edu.nlu.fit.demo1.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Date;

public class EmailUtil {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "emailgroup15@gmail.com";
    private static final String EMAIL_PASSWORD = "passwordemailgroup15";
    private static final String FROM_EMAIL = "Group15 Hotel <emailemailgroup15@gmail.com>";

    public static boolean sendVerificationEmail(String toEmail, String fullName, String verificationLink) {
        String subject = "Xác thực tài khoản - Group15 Hotel Booking";
        String htmlContent = buildVerificationEmailHtml(fullName, verificationLink);

        return sendHtmlEmail(toEmail, subject, htmlContent);
    }

    public static boolean sendPasswordResetEmail(String toEmail, String fullName, String resetLink) {
        String subject = "Đặt lại mật khẩu - Group15 Hotel Booking";
        String htmlContent = buildPasswordResetEmailHtml(fullName, resetLink);

        return sendHtmlEmail(toEmail, subject, htmlContent);
    }


    private static boolean sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setSentDate(new Date());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
            return true;

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static String buildVerificationEmailHtml(String fullName, String verificationLink) {
        return "<!DOCTYPE html>" +
                "<html lang='vi'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #0057FF, #0044cc); padding: 40px 20px; text-align: center; color: white; }" +
                "        .header h1 { margin: 0; font-size: 28px; }" +
                "        .content { padding: 40px 30px; }" +
                "        .content h2 { color: #0057FF; margin-top: 0; }" +
                "        .content p { margin: 15px 0; font-size: 16px; }" +
                "        .btn-verify { display: inline-block; padding: 15px 40px; background: #0057FF; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; margin: 20px 0; transition: background 0.3s; }" +
                "        .btn-verify:hover { background: #0044cc; }" +
                "        .footer { background: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; }" +
                "        .info-box { background: #f0f7ff; border-left: 4px solid #0057FF; padding: 15px; margin: 20px 0; border-radius: 4px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>🏨 Group15 Hotel Booking</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <h2>Xin chào " + fullName + "!</h2>" +
                "            <p>Cảm ơn bạn đã đăng ký tài khoản tại <strong>Group15 Hotel Booking</strong>.</p>" +
                "            <p>Để hoàn tất quá trình đăng ký và kích hoạt tài khoản, vui lòng nhấn vào nút bên dưới:</p>" +
                "            <div style='text-align: center;'>" +
                "                <a href='" + verificationLink + "' class='btn-verify'>Xác Thực Tài Khoản</a>" +
                "            </div>" +
                "            <div class='info-box'>" +
                "                <p style='margin: 0;'><strong>⏰ Lưu ý:</strong> Link xác thực chỉ có hiệu lực trong <strong>24 giờ</strong>.</p>" +
                "            </div>" +
                "            <p>Nếu bạn không thực hiện đăng ký này, vui lòng bỏ qua email này.</p>" +
                "            <p>Nếu nút không hoạt động, bạn có thể sao chép và dán link sau vào trình duyệt:</p>" +
                "            <p style='word-break: break-all; background: #f8f9fa; padding: 10px; border-radius: 4px; font-size: 14px;'>" + verificationLink + "</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>© 2025 Group15 Hotel Booking. All rights reserved.</p>" +
                "            <p>Email: support@group15.com | Phone: +1 (800) 123 456</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Build HTML cho email đặt lại mật khẩu
     */
    private static String buildPasswordResetEmailHtml(String fullName, String resetLink) {
        return "<!DOCTYPE html>" +
                "<html lang='vi'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #EF4444, #dc2626); padding: 40px 20px; text-align: center; color: white; }" +
                "        .header h1 { margin: 0; font-size: 28px; }" +
                "        .content { padding: 40px 30px; }" +
                "        .content h2 { color: #EF4444; margin-top: 0; }" +
                "        .content p { margin: 15px 0; font-size: 16px; }" +
                "        .btn-reset { display: inline-block; padding: 15px 40px; background: #EF4444; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; margin: 20px 0; transition: background 0.3s; }" +
                "        .btn-reset:hover { background: #dc2626; }" +
                "        .footer { background: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; }" +
                "        .warning-box { background: #fff3cd; border-left: 4px solid #F59E0B; padding: 15px; margin: 20px 0; border-radius: 4px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>🔒 Đặt Lại Mật Khẩu</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <h2>Xin chào " + fullName + "!</h2>" +
                "            <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại <strong>Group15 Hotel Booking</strong>.</p>" +
                "            <p>Để đặt lại mật khẩu, vui lòng nhấn vào nút bên dưới:</p>" +
                "            <div style='text-align: center;'>" +
                "                <a href='" + resetLink + "' class='btn-reset'>Đặt Lại Mật Khẩu</a>" +
                "            </div>" +
                "            <div class='warning-box'>" +
                "                <p style='margin: 0;'><strong>⚠️ Cảnh báo:</strong> Link đặt lại mật khẩu chỉ có hiệu lực trong <strong>1 giờ</strong>.</p>" +
                "            </div>" +
                "            <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này và mật khẩu của bạn sẽ không thay đổi.</p>" +
                "            <p>Nếu nút không hoạt động, bạn có thể sao chép và dán link sau vào trình duyệt:</p>" +
                "            <p style='word-break: break-all; background: #f8f9fa; padding: 10px; border-radius: 4px; font-size: 14px;'>" + resetLink + "</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>© 2025 Group15 Hotel Booking. All rights reserved.</p>" +
                "            <p>Email: support@group15.com | Phone: +1 (800) 123 456</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}