package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DangKyKhoaHocRepository extends MongoRepository<DangKyKhoaHoc, String> {
        Optional<DangKyKhoaHoc>
    findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
            String userId,
            String courseId
    );
    List<DangKyKhoaHoc> findByNguoiHoc_IdDoiTuong(String userId);
    Boolean existsByKhoaHoc_IdKhoaHoc(String courseId);
    @Query(value = "{ 'user.$id': ObjectId(?0) }",
            fields = "{ 'khoaHoc.$id': 1 }")
    List<DangKyKhoaHoc> findCourseRefsByUser(String userId);
    long countByThoiGianDangKyBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    List<DangKyKhoaHoc> findByThoiGianDangKyBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    @Aggregation(pipeline = {
            "{ $match: { thoiGianDangKy: { $gte: ?0, $lte: ?1 } } }",
            "{ $lookup: { from: 'khoa_hoc', localField: 'khoaHoc.$id', foreignField: '_id', as: 'course' } }",
            "{ $unwind: '$course' }",
            "{ $group: { _id: null, totalRevenue: { $sum: '$course.giaBan' } } }"
    })
    Double sumRevenueByTime(LocalDateTime start, LocalDateTime end);
}
