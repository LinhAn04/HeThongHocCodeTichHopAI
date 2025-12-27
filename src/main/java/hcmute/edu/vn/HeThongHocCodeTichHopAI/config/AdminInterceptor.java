package hcmute.edu.vn.HeThongHocCodeTichHopAI.config;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        if (!LoaiDoiTuongSuDung.ADMIN.equals(user.getLoaiDoiTuongSuDung())) {
            response.sendRedirect("/courses");
            return false;
        }

        return true;
    }
}
