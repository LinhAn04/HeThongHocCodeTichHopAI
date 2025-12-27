package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private DoiTuongSuDungRepository repo;

    public List<DoiTuongSuDung> getAllStudents() {
        return repo.findByLoaiDoiTuongSuDung(LoaiDoiTuongSuDung.STUDENT);
    }
}