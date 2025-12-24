package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiBlogService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blogs")
public class BlogPageController {

    private final IBaiBlogService blogService;
    private final IDoiTuongSuDungService userService;

    public BlogPageController(
            IBaiBlogService blogService,
            IDoiTuongSuDungService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView blogList(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("blog_list");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = email != null;
        mv.addObject("loggedIn", loggedIn);

        if (loggedIn) {
            mv.addObject("user", userService.findByEmail(email));
        }

        mv.addObject("activeMenu", "blogs");
        mv.addObject("blogs", blogService.findAll());

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView blogDetail(
            @PathVariable String id,
            HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("blog_detail");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = email != null;
        mv.addObject("loggedIn", loggedIn);

        if (loggedIn) {
            mv.addObject("user", userService.findByEmail(email));
        }

        BaiBlog blog = blogService.findById(id);
        if (blog == null) {
            mv.setViewName("redirect:/blogs");
            return mv;
        }

        mv.addObject("blog", blog);
        return mv;
    }
}

