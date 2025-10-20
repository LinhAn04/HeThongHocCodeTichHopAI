package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.HoaDon;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.HoaDonRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IHoaDonService;

import java.util.List;

@Service
public class HoaDonService implements IHoaDonService {
    private final HoaDonRepository hoaDonRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    @Override
    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDon findById(String id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public void deleteById(String id) {
        hoaDonRepository.deleteById(id);
    }
}
