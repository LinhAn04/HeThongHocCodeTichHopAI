package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import java.util.ArrayList;
import java.util.List;

public class QuizForm {
    private List<CauHoiTracNghiem> quizCauHoi = new ArrayList<>();

    public List<CauHoiTracNghiem> getQuizCauHoi() {
        return quizCauHoi;
    }

    public void setQuizCauHoi(List<CauHoiTracNghiem> quizCauHoi) {
        this.quizCauHoi = quizCauHoi;
    }
}
