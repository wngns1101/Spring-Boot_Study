package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticle() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
//        System.out.println(articleForm.toString());
        log.info(articleForm.toString());
        // 1.DTO 엔티티 변환
        Article article = articleForm.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
//        Optional<Article> article = articleRepository.findById(id);
        // 1. repository 메서드 호출
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> comments = commentService.comments(id);
        // 2. model에 호출한 데이터 저장
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", comments);
        // 3. 페이지 반환
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articles = (List<Article>)articleRepository.findAll();
        model.addAttribute("articleList", articles);
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article editArticle = articleRepository.findById(id).orElse(null);
        log.info(editArticle.toString());
        model.addAttribute("article", editArticle);
        return "/articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm articleForm){
        log.info(articleForm.toString());

        Article articleEntity = articleForm.toEntity();
        log.info(articleEntity.toString());

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("delete");
        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());
        if(article != null){
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        return "redirect:/articles";
    }
}
