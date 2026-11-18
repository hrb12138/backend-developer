package me.scpark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.scpark.domain.Article;

@Getter//创建get函数
//public String getTitle(){return title;}public String getContent() {return content;}
@NoArgsConstructor//创建无参构造
//public AddArticleRequest(){
//    super();
//}
@AllArgsConstructor//创建有参构造
//    public AddArticleRequest(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
public class AddArticleRequest {
    private String title;
    private String content;


    public Article toEntity(){
        //return new Article(title , content);
        return Article.builder().title(title).content(content).build();
    }

}
