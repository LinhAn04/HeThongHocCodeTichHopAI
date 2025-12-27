package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

// Quản lý CRUD blog
@Controller
@RequestMapping("/admin/blogs")
public class AdminBlogController {

    private final IBaiBlogService blogService;
    private final IDoiTuongSuDungService userService;

    public AdminBlogController(
            IBaiBlogService blogService,
            IDoiTuongSuDungService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model, HttpSession session,
                       HttpServletRequest request) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        ModelAndView mv = new ModelAndView();

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);

        mv.addObject("loggedIn", loggedIn);
        model.addAttribute("user", user);
        model.addAttribute("blogs", blogService.findAll());
        model.addAttribute("activeMenu", "blogs");

        return "admin/blogs_management/admin_blogs";
    }

    @GetMapping("/new")
    public String create(Model model, HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("blog", new BaiBlog());
        model.addAttribute("isEdit", false);

        return "admin/blogs_management/admin_blog_form";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable String id,
            Model model,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        BaiBlog blog = blogService.findById(id);
        if (blog == null) return "redirect:/admin/blogs";

        model.addAttribute("user", user);
        model.addAttribute("blog", blog);
        model.addAttribute("isEdit", true);

        return "admin/blogs_management/admin_blog_form";
    }

    @PostMapping("/save")
    public String create(
            @ModelAttribute BaiBlog blog,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        blog.setTacGia(user);
        blog.setThoiGianDangBai(LocalDateTime.now());

        blogService.save(blog);
        return "redirect:/admin/blogs";
    }

    @PostMapping("/save/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute BaiBlog blog,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        BaiBlog oldBlog = blogService.findById(id);
        if (oldBlog == null) return "redirect:/admin/blogs";

        blog.setIdBaiBlog(id); // 👈 GÁN TAY ID
        blog.setTacGia(oldBlog.getTacGia());
        blog.setThoiGianDangBai(oldBlog.getThoiGianDangBai());

        blogService.save(blog);
        return "redirect:/admin/blogs";
    }


    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable String id,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        blogService.deleteById(id);
        return "redirect:/admin/blogs";
    }

    @PostMapping("/upload-cover")
    @ResponseBody
    public Map<String, Object> uploadCover(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file.isEmpty()) {
                return Map.of("success", false);
            }

            String uploadDir = "uploads/admin/blogs/";
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            String fileName = "blog_" + System.currentTimeMillis() + ext;
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            return Map.of(
                    "success", true,
                    "path", "/uploads/admin/blogs/" + fileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false);
        }
    }
}