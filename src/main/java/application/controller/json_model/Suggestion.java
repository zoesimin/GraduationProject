package application.controller.json_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creator: DreamBoy
 * Date: 2019/6/1.
 */
public class Suggestion {
    private String problemNodeTopic;
    private String sourceNodeTopic;
    private String reason;

    public String getProblemNodeTopic() {
        return problemNodeTopic;
    }

    public void setProblemNodeTopic(String problemNodeTopic) {
        this.problemNodeTopic = problemNodeTopic;
    }

    public String getSourceNodeTopic() {
        return sourceNodeTopic;
    }

    public void setSourceNodeTopic(String sourceNodeTopic) {
        this.sourceNodeTopic = sourceNodeTopic;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
