package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(Article article, CommentDto commentDto) throws IllegalAccessException {
        if (commentDto.getId() != null) {
            throw new IllegalAccessException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        if (commentDto.getArticleId() != article.getId()) {
            throw new IllegalAccessException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        }
        return new Comment(commentDto.getId(), article, commentDto.getNickname(), commentDto.getBody());
    }

    public void patch(CommentDto commentDto) {
        if (this.id != commentDto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 아이디가 입력됐습니다.");
        }
        if (commentDto.getNickname() != null) {
            this.nickname = commentDto.getNickname();
        }
        if (commentDto.getBody() != null) {
            this.body = commentDto.getBody();
        }
    }
}
