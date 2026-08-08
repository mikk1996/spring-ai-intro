package guru.springframework.springaiintro.service;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;

public interface OpenAiService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest request);

    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
