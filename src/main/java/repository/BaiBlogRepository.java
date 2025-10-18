package repository;

import model.BaiBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaiBlogRepository extends JpaRepository<BaiBlog, Integer> {
}
