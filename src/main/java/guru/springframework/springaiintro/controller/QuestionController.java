package guru.springframework.springaiintro.controller;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;
import guru.springframework.springaiintro.service.OpenAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OpenAiService openAiService;

    public QuestionController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWIthInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAiService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAiService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(Question question) {
        return openAiService.getAnswer(question);
    }
}
