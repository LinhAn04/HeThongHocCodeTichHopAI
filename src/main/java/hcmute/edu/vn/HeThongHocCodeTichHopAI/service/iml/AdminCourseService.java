package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminCourseService {

    private final KhoaHocRepository courseRepo;
    private final DangKyKhoaHocRepository enrollRepo;
    private final BaiHocRepository lessonRepo;

    public AdminCourseService(KhoaHocRepository courseRepo,
                              DangKyKhoaHocRepository enrollRepo,
                              BaiHocRepository lessonRepo) {
        this.courseRepo = courseRepo;
        this.enrollRepo = enrollRepo;
        this.lessonRepo = lessonRepo;
    }

    public List<KhoaHoc> findAll() {
        return courseRepo.findAll();
    }

    public KhoaHoc findById(String id) {
        return courseRepo.findById(id).orElseThrow();
    }

    public void save(KhoaHoc course) {
        if (course.getNgayTao() == null) {
            course.setNgayTao(LocalDateTime.now());
            course.setIsActive(true);
        }
        courseRepo.save(course);
    }

    public boolean hasEnrollment(String courseId) {
        return enrollRepo.existsByKhoaHoc_IdKhoaHoc(courseId);
    }

    public List<BaiHoc> findLessonsByCourse(String courseId) {
        return lessonRepo
                .findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(courseId);
    }

    public void deleteOrHide(String id) {
        KhoaHoc course = findById(id);

        if (Boolean.TRUE.equals(course.getHasEnrollment())) {
            course.setIsActive(false);
            courseRepo.save(course);
        } else {
            courseRepo.delete(course);
        }
    }

    public void hide(String id) {
        KhoaHoc c = findById(id);
        c.setIsActive(false);
        courseRepo.save(c);
    }

    public void unhide(String id) {
        KhoaHoc c = findById(id);
        c.setIsActive(true);
        courseRepo.save(c);
    }

    public Map<String, Boolean> buildCanDeleteMap(List<KhoaHoc> courses) {
        Map<String, Boolean> map = new HashMap<>();
        for (KhoaHoc c : courses) {
            map.put(
                    c.getIdKhoaHoc(),
                    !Boolean.TRUE.equals(c.getHasEnrollment())
            );
        }
        return map;
    }

    public List<KhoaHoc> findAllActive() {
        return courseRepo.findByIsActiveTrue();
    }
}
