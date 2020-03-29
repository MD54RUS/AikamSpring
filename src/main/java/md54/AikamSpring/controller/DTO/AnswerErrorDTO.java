package md54.AikamSpring.controller.DTO;

public class AnswerErrorDTO extends AnswerTemplate {
    String message;

    public AnswerErrorDTO(String message) {
        type = "error";
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
