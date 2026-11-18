package me.scpark.controller;

import lombok.RequiredArgsConstructor;
import me.scpark.domain.Article;
import me.scpark.dto.AddArticleRequest;
import me.scpark.dto.ArticleResponse;
import me.scpark.dto.UpdateArticleRequest;
import me.scpark.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
//    public BlogApiController(BlogService service){
//        this.blogService=service;
//    }
@RestController
public class BlogApiController {
    private final BlogService blogService;
//    new BlogApiController(service);

    @PostMapping("/api/articles")
//    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
//        Article savedArticle = blogService.save(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
//    }
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        ArticleResponse articleResponse=new ArticleResponse(savedArticle);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponse);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
//        blogService.
        List<Article> articleList=blogService.getAll();
//        List<ArticleResponse> articleResponseList=new ArrayList<>();
//        for(Article article: articleList){
//            ArticleResponse ar=new ArticleResponse(article);
//            articleResponseList.add(ar);
//        }
        List<ArticleResponse> articleResponseList=articleList
                .stream()
                //.map(a->new ArticleResponse(a))
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articleResponseList);

    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id){
        Article article=blogService.getById(id);
        ArticleResponse articleResponse=new ArticleResponse(article);
        return ResponseEntity.ok().body(articleResponse);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id){
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/api/articles")
    public ResponseEntity<Void> deleteArticleAll(){
        blogService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id,@RequestBody UpdateArticleRequest request){
        Article article=blogService.updateArticle(id,request);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

}
