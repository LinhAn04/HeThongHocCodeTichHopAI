package service.iml;

import model.HoaDon;
import org.springframework.stereotype.Service;
import repository.HoaDonRepository;
import service.IHoaDonService;

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
    public HoaDon findById(int id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public void deleteById(int id) {
        hoaDonRepository.deleteById(id);
    }
}
