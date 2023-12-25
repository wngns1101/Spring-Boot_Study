package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시물의 모든 댓글 조회")
    void findByArticleId() {
        {
            // 1. 입력 데이터
            Long articleId = 4L;
            // 2. 실제 데이터
            List<Comment> byArticleId = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱㄱ");
            Comment a = new Comment(1L, article, "Park", "굿 윌헌팅");
            Comment b = new Comment(2L, article, "lee", "yammuy");
            Comment c = new Comment(3L, article, "choi", "영웅");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 4. 비교 및 검증
            assertEquals(expected.toString(), byArticleId.toString());
        }
        {
            // 1. 입력 데이터
            Long articleId = 1L;
            // 2. 실제 데이터
            List<Comment> byArticleId = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), byArticleId.toString(), "1번 글은 댓글이 없음");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        {
            // 1. 입력 데이터
            String nickname = "Park";
            // 2. 실제 데이터
            List<Comment> byNickname = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱㄱ"), nickname, "굿 윌헌팅");
            Comment b = new Comment(5L, new Article(5L, "당신의 인생 드라마는?", "댓글 ㄱㄱㄱ"), nickname, "2");
            Comment c = new Comment(9L, new Article(6L, "당신의 인생 음악은?", "댓글 ㄱㄱㄱㄱ"), nickname, "1");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 4. 비교 및 검증
            assertEquals(expected.toString(), byNickname.toString());
        }
    }

}