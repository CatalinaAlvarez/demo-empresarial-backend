package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
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

@SpringBootTest
class GetUseCaseTest {

    @MockBean
    private QuestionRepository questionRepository;
    @SpyBean
    private GetUseCase getQuestion;

    @Test
    public void getQuestion(){

        var questionDTO = new QuestionDTO("1"
                ,"1",
                "¿What is java?",
                Type.OPEN,
                Category.TECHNOLOGY_AND_COMPUTER);

        var question= new Question();
        question.setId("1");
        question.setQuestion("¿What is java?");
        question.setUserId("1");
        question.setType(Type.OPEN);
        question.setCategory(Category.TECHNOLOGY_AND_COMPUTER);

        Mockito.when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));

        var respuesta = getQuestion.apply("1");
        Assertions.assertEquals(respuesta.block().getQuestion(), question.getQuestion());
        Assertions.assertEquals(respuesta.block().getId(), question.getId());
        Assertions.assertEquals(respuesta.block().getUserId(), question.getUserId());
        Assertions.assertEquals(respuesta.block().getType(), question.getType());
        Assertions.assertEquals(respuesta.block().getCategory(), question.getCategory());

        Mockito.verify(questionRepository,Mockito.times(1)).findById("1");
    }


}