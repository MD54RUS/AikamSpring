package md54.AikamSpring.controller.InputOutput;

import md54.AikamSpring.controller.DTO.AnswerTemplate;

public interface Writer {
    void setDest(String dest);

    void write(AnswerTemplate answer);
}
