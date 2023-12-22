package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    // Get
    @GetMapping("/api/articles")
    public List<Article> index() {
        return (List<Article>) articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article entity = dto.toEntity();
        return articleRepository.save(entity);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        // 1. DTO -> 엔티티 변환
        Article entity = dto.toEntity();
        // 2. 타깃 조회
        Article article = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리
        if (entity == null || article.getId() != id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 업데이트 및 응답 처리
        article.patch(entity);
        Article update = articleRepository.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        articleRepository.delete(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
