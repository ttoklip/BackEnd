package com.domain.question.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.repository.post.QuestionRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeQuestionPostRepository implements QuestionRepository {

    private final Map<Long, Question> memoryRepository = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public Question findByIdActivated(final Long questionId) {
        return memoryRepository.values().stream()
                .filter(question -> question.getId().equals(questionId) && !question.isDeleted())
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
    }

    // ToDo fake query impl
    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
        // 별도의 Comment 저장소가 없다는 가정 하에 기본 구현
        return null;
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        return findByIdActivated(questionPostId);
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
        Question question = findByIdActivated(questionId);
        if (question == null) {
            return Collections.emptyList();
        }
        // 삭제되지 않은 댓글만 반환
        return question.getQuestionComments().stream()
                .filter(comment -> !comment.isDeleted())
                .toList();
    }

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        List<Question> filteredQuestions = memoryRepository.values().stream()
                .filter(question -> question.getCategory().equals(category))
                .sorted(Comparator.comparing(Question::getId).reversed())
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredQuestions.size());
        List<Question> pagedQuestions = filteredQuestions.subList(start, end);
        return new PageImpl<>(pagedQuestions, pageable, filteredQuestions.size());
    }

    @Override
    public List<Question> getHouseWork() {
        return findByCategory(Category.HOUSEWORK);
    }

    @Override
    public List<Question> getRecipe() {
        return findByCategory(Category.RECIPE);
    }

    @Override
    public List<Question> getSafeLiving() {
        return findByCategory(Category.SAFE_LIVING);
    }

    @Override
    public List<Question> getWelfarePolicy() {
        return findByCategory(Category.WELFARE_POLICY);
    }

    private List<Question> findByCategory(Category category) {
        List<Question> result = memoryRepository.values().stream()
                .filter(question -> question.getCategory().equals(category))
                .sorted(Comparator.comparing(Question::getId).reversed())
                .limit(10)
                .toList();

        if (result.isEmpty()) {
            throw new ApiException(ErrorType.QUESTION_NOT_FOUND);
        }
        return result;
    }

    @Override
    public Question save(final Question question) {
        idCounter++;
        Question savedQuestion = Question.builder()
                .id(idCounter)
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory())
                .member(question.getMember())
                .questionComments(question.getQuestionComments())
                .build();
        memoryRepository.put(idCounter, savedQuestion);
        return savedQuestion;
    }
}
