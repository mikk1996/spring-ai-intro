package guru.springframework.springaiintro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final ChatClient chatClient;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPrompt;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAiServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
       PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));

        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return response.getResult().getOutput().getText();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest request) {
        BeanOutputConverter<GetCapitalResponse> parser = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = parser.getFormat();

        // PromptTemplate promptTemplate = new PromptTemplate("what is the capital of " + request.stateOrCountry() + "?");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry", request.stateOrCountry()
                "format", format
        ));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        String responseString;
        JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getText());

        responseString = jsonNode.get("answer").asString();

        return parser.convert(response.getResult().getOutput().getText());
    }
}
