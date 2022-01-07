package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.utils.Category;
import co.com.sofka.questions.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FindAllByCategoryUseCaseTest {

    @MockBean
    QuestionRepository repository;

    @SpyBean
    FindAllByCategoryUseCase useCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(QuestionRepository.class);
        useCase = new FindAllByCategoryUseCase(mapperUtils, repository);
    }

    @Test
    void findAllByCategoryListTest() {

        var question = new Question("11",
                "xxxx",
                "What is java?",
                Type.OPEN,
                Category.SCIENCES);

        when(repository.findAllByCategory(question.getCategory().toString())).thenReturn(Flux.just(question));

        StepVerifier.create(useCase.apply(question.getCategory().toString()))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getUserId().equals("xxxx");
                    assert questionDTO.getCategory().equals(Category.SCIENCES);
                    assert questionDTO.getQuestion().equals("What is java?");
                    assert questionDTO.getType().equals(Type.OPEN);
                    return true;
                })
                .verifyComplete();
        verify(repository).findAllByCategory(question.getCategory().toString());
    }
}