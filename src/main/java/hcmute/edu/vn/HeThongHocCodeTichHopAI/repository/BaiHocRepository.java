package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BaiHocRepository extends MongoRepository<BaiHoc, String> {
    List<BaiHoc> findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(String idKhoaHoc);
    BaiHoc findFirstByKhoaHoc_IdKhoaHocOrderByThuTuAsc(String idKhoaHoc);
    BaiHoc findFirstByKhoaHoc_IdKhoaHocAndThuTuGreaterThanOrderByThuTuAsc(String idKhoaHoc, int thuTu);
    BaiHoc findFirstByKhoaHoc_IdKhoaHocAndThuTuLessThanOrderByThuTuDesc(String idKhoaHoc, int thuTu);
    List<BaiHoc> findByKhoaHoc_IdKhoaHoc(String idKhoaHoc);
}
