package service.iml;

import model.TKDoiTuongSuDung;
import org.springframework.stereotype.Service;
import repository.TKDoiTuongSuDungRepository;
import service.ITKDoiTuongSuDungService;

import java.util.List;

@Service
public class TKDoiTuongSuDungService implements ITKDoiTuongSuDungService {
    private final TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository;

    public TKDoiTuongSuDungService(TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository) {
        this.tkDoiTuongSuDungRepository = tkDoiTuongSuDungRepository;
    }

    @Override
    public List<TKDoiTuongSuDung> findAll() {
        return tkDoiTuongSuDungRepository.findAll();
    }

    @Override
    public TKDoiTuongSuDung findById(int id) {
        return tkDoiTuongSuDungRepository.findById(id).orElse(null);
    }

    @Override
    public TKDoiTuongSuDung save(TKDoiTuongSuDung tkDoiTuongSuDung) {
        return tkDoiTuongSuDungRepository.save(tkDoiTuongSuDung);
    }

    @Override
    public void deleteById(int id) {
        tkDoiTuongSuDungRepository.deleteById(id);
    }
}
