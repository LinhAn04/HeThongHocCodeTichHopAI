package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoiTuongSuDungRepository extends MongoRepository<DoiTuongSuDung, String> {
    DoiTuongSuDung findByEmail(String email);
    List<DoiTuongSuDung> findByLoaiDoiTuongSuDung(LoaiDoiTuongSuDung loai);
}
