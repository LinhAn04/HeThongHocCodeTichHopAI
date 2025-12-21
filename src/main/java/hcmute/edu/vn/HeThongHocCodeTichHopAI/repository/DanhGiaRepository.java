package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DanhGia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DanhGiaRepository extends MongoRepository<DanhGia, String> {
    List<DanhGia> findByKhoaHoc_IdKhoaHocOrderByThoiGianDanhGiaDesc(String idKhoaHoc);
}
