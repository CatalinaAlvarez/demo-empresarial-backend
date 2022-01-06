package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
@Validated
public class FindAllByCategoryUseCase {

    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public FindAllByCategoryUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    public Flux<QuestionDTO> apply(String category) {
        Objects.requireNonNull(category, "Category is required");
        return questionRepository.findAllByCategory(category)
                .map(mapperUtils.mapEntityToQuestion());
    }
}
