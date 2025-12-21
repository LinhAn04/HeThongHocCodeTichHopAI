package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.PhanHoiDanhGia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhanHoiDanhGiaRepository extends MongoRepository<PhanHoiDanhGia, String> {
    List<PhanHoiDanhGia> findByDanhGia_IdDanhGiaOrderByThoiGianPhanHoiAsc(String idDanhGia);
}
