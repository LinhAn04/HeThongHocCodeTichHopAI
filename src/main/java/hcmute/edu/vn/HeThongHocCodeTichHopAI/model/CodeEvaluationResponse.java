package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeEvaluationResponse {

    private int score;
    private String evaluation;
    private List<String> improvements;

    public CodeEvaluationResponse() {
    }

    public CodeEvaluationResponse(int score, String evaluation, List<String> improvements) {
        this.score = score;
        this.evaluation = evaluation;
        this.improvements = improvements;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }
}