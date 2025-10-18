package service.iml;

import model.DoiTuongSuDung;
import org.springframework.stereotype.Service;
import repository.DoiTuongSuDungRepository;
import service.IDoiTuongSuDungService;

import java.util.List;

@Service
public class DoiTuongSuDungService implements IDoiTuongSuDungService {
    private final DoiTuongSuDungRepository doiTuongSuDungRepository;

    public DoiTuongSuDungService(DoiTuongSuDungRepository doiTuongSuDungRepository) {
        this.doiTuongSuDungRepository = doiTuongSuDungRepository;
    }

    @Override
    public List<DoiTuongSuDung> findAll() {
        return doiTuongSuDungRepository.findAll();
    }

    @Override
    public DoiTuongSuDung findById(int id) {
        return doiTuongSuDungRepository.findById(id).orElse(null);
    }

    @Override
    public DoiTuongSuDung save(DoiTuongSuDung doiTuongSuDung) {
        return doiTuongSuDungRepository.save(doiTuongSuDung);
    }

    @Override
    public void deleteById(int id) {
        doiTuongSuDungRepository.deleteById(id);
    }
}