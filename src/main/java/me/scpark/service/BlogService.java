package me.scpark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.scpark.domain.Article;
import me.scpark.dto.AddArticleRequest;
import me.scpark.dto.UpdateArticleRequest;
import me.scpark.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    //@Autowired
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest dto){
        return blogRepository.save(dto.toEntity());
    }
    public List<Article> getAll(){
        return blogRepository.findAll();
    }
    public Article getById(Long id){
        return blogRepository.getReferenceById(id);//findById(id).get()
    }
    public void deleteById(Long id){
        blogRepository.deleteById(id);
    }
    public void deleteAll(){
        blogRepository.deleteAll();
    }

    @Transactional
    public Article updateArticle(Long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("그런 블로그 글은 존재하지 않음["+id+"]"));
        article.update(request.getTitle(),request.getContent());
        blogRepository.save(article);
        return article;
//        Article article=new Article(request.getTitle(),request.getContent());
//        article.setId(id);
//        blogRepository.save(article);
//        return article;
    }
}
