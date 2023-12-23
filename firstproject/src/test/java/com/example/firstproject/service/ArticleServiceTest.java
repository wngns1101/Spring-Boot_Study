package com.example.firstproject.service;

import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        Article a = new Article(1L, "aaa", "111");
        Article b = new Article(2L, "bbb", "222");
        Article c = new Article(3L, "ccc", "333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        List<Article> index = articleService.index();

        assertEquals(expected.toString(), index.toString());
    }

    @Test
    void show_성공() {
        Long id = 1L;
        Article expected = new Article(id, "gagagaga", "1111");

        Article show = articleService.show(id);

        assertEquals(expected.toString(), show.toString());
    }

    @Test
    void show_실패() {
        Long id = -1L;
        Article expected = null;

        Article show = articleService.show(id);

        assertEquals(expected, show);
    }

    @Test
    void create_성공() {

    }

    @Test
    void create_실패() {
    }
}