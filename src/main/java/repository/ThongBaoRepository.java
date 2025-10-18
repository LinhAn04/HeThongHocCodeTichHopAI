package repository;

import model.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {
}
