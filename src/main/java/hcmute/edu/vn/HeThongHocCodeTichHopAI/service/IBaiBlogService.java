package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;

import java.util.List;

public interface IBaiBlogService {
    List<BaiBlog> findAll();
    BaiBlog findById(String id);
    BaiBlog save(BaiBlog baiBlog);
    void deleteById(String id);
}
