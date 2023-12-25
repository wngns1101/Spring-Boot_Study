package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        List<Comment> byArticleId = commentRepository.findByArticleId(articleId);
        return byArticleId.stream().map(CommentDto::createCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto commentDto) throws IllegalAccessException {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시물이 없습니다"));

        Comment comment = Comment.createComment(article, commentDto);

        Comment save = commentRepository.save(comment);

        return CommentDto.createCommentDto(save);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        comment.patch(commentDto);

        Comment updated = commentRepository.save(comment);

        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다."));
        commentRepository.delete(comment);
        return CommentDto.createCommentDto(comment);
    }
}
