package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

public class LessonForm {
    private BaiHoc lesson;
    private QuizForm quizForm;

    public BaiHoc getLesson() {
        return lesson;
    }

    public void setLesson(BaiHoc lesson) {
        this.lesson = lesson;
    }

    public QuizForm getQuizForm() {
        return quizForm;
    }

    public void setQuizForm(QuizForm quizForm) {
        this.quizForm = quizForm;
    }
}
