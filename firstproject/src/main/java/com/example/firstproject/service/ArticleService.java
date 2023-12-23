package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return (List<Article>) articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article entity = dto.toEntity();
        if (entity.getId() != null) {
            return null;
        }
        return articleRepository.save(entity);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. DTO -> 엔티티 변환
        Article entity = dto.toEntity();
        // 2. 타깃 조회
        Article article = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리
        if (entity == null || article.getId() != id) {
            log.info("잘못된 요청");
            return null;
        }
        // 4. 업데이트 및 응답 처리
        article.patch(entity);
        Article update = articleRepository.save(entity);
        return update;
    }

    public Article delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            log.info("잘못된 요청");
            return null;
        }

        articleRepository.delete(article);
        return article;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> collect = dtos.stream()
                .map(ArticleForm::toEntity)
                .toList();

        collect.stream().forEach(article -> articleRepository.save(article));

        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패"));

        return collect;
    }
}
