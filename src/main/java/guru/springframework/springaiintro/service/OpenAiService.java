package guru.springframework.springaiintro.service;

import guru.springframework.springaiintro.model.*;

public interface OpenAiService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest request);

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
