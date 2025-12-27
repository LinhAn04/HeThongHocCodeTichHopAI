package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        var webRequest = new ServletWebRequest(request);
        var attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        model.addAttribute("status", attrs.get("status")); // ex: 400
        model.addAttribute("error", attrs.get("error"));   // ex: Bad Request
        return "error";
    }

    @ModelAttribute("backUrl")
    public String backUrl(HttpSession session) {

        Object obj = session.getAttribute("user");

        if (obj == null) {
            return "/courses";
        }
        DoiTuongSuDung user = (DoiTuongSuDung) obj;

        if (user.getLoaiDoiTuongSuDung().name().equals("ADMIN")) {
            return "/admin/users";
        }
        return "/courses";
    }
}
