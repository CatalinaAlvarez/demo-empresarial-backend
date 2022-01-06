package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.utils.Category;
import co.com.sofka.questions.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

class ListUseCaseTest {

     QuestionRepository repository;
     ListUseCase listUseCase;


    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(QuestionRepository.class);
        listUseCase = new ListUseCase(mapperUtils, repository);
    }

    @Test
     void getValidationTest(){
        var question =  new Question();
        question.setUserId("xxxx-xxxx");
        question.setType(Type.OPEN);
        question.setCategory(Category.LANGUAGE);
        question.setQuestion("¿Que es java?");
        when(repository.findAll()).thenReturn(Flux.just(question ));

        StepVerifier.create(listUseCase.get())
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getUserId().equals("xxxx-xxxx");
                    assert questionDTO.getCategory().equals(Category.LANGUAGE);
                    assert questionDTO.getQuestion().equals("¿Que es java?");
                    assert questionDTO.getType().equals(Type.OPEN);
                    return true;
                })
                .verifyComplete();

        verify(repository).findAll();
    }
}