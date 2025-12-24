package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

public class EnrollChatState {
    private EnrollChatStep step;
    private String q1Correct;
    private String q2Correct;

    public EnrollChatStep getStep() {
        return step;
    }

    public void setStep(EnrollChatStep step) {
        this.step = step;
    }

    public String getQ1Correct() {
        return q1Correct;
    }

    public void setQ1Correct(String q1Correct) {
        this.q1Correct = q1Correct;
    }

    public String getQ2Correct() {
        return q2Correct;
    }

    public void setQ2Correct(String q2Correct) {
        this.q2Correct = q2Correct;
    }
}
