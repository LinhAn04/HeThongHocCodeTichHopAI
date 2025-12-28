package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KhoaHocRepository extends MongoRepository<KhoaHoc, String> {
    List<KhoaHoc> findByIsActiveTrue();

    @Query("""
        {
          $or: [
            { isActive: true },
            { _id: { $in: ?0 } }
          ]
        }
        """)
    List<KhoaHoc> findVisibleCoursesForUser(List<String> enrolledCourseIds);
}
