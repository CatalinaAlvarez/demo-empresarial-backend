package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.utils.Category;
import co.com.sofka.questions.utils.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddAnswerUseCaseTest {
    @SpyBean
    AddAnswerUseCase addAnswerUseCase;

    @MockBean
    GetUseCase getUseCase;

    @MockBean
    AnswerRepository answerRepository;

    @Test
    void addAnswerTest() {
        var question = new QuestionDTO("11",
                "xxxx",
                "What is java?",
                Type.OPEN,
                Category.SCIENCES);

        var answerDTO = new AnswerDTO("1",
                "xxxx",
                "Es un lenguaje de programación y otras palabras");

        var answer = new Answer("11",
                "xxxx",
                "1",
                "Es un lenguaje de programación y otras palabras", 1);

        when(answerRepository.save(any())).thenReturn(Mono.just(answer));
        when(getUseCase.apply(any())).thenReturn(Mono.just(question));

        var questionDTO = addAnswerUseCase.apply(answerDTO);
        var resultQuestionDTO = questionDTO.block();

        assert resultQuestionDTO != null;
        Assertions.assertEquals(resultQuestionDTO.getId(),question.getId());
        Assertions.assertEquals(resultQuestionDTO.getQuestion(),question.getQuestion());
        Assertions.assertEquals(resultQuestionDTO.getAnswers().get(0).getQuestionId(),answerDTO.getQuestionId());
        Assertions.assertEquals(resultQuestionDTO.getAnswers().get(0).getAnswer(),answerDTO.getAnswer());

        Mockito.verify(answerRepository,Mockito.times(1)).save(any());
    }
}