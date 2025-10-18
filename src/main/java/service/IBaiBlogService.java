package service;

import model.BaiBlog;

import java.util.List;

public interface IBaiBlogService {
    List<BaiBlog> findAll();
    BaiBlog findById(int id);
    BaiBlog save(BaiBlog baiBlog);
    void deleteById(int id);
}
