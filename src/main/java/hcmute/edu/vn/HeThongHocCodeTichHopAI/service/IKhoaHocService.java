package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;

import java.util.List;

public interface IKhoaHocService {
    List<KhoaHoc> findAll();
    KhoaHoc findById(String id);
    KhoaHoc save(KhoaHoc khoaHoc);
    void deleteById(String id);
    List<KhoaHoc> findActiveCourses();
    List<KhoaHoc> findVisibleCoursesForUser(List<String> enrolledCourseIds);

}
