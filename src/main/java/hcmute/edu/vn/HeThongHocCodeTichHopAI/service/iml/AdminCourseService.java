package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.KhoaHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCourseService {

    @Autowired
    private KhoaHocRepository repo;

    public List<KhoaHoc> findAll() {
        return repo.findAll();
    }

    public KhoaHoc findById(String id) {
        return repo.findById(id).orElse(null);
    }

    public void save(KhoaHoc course) {
        repo.save(course);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}

