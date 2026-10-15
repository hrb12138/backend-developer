package me.scpark;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)    //自动生成无参数构造函数
@AllArgsConstructor  //自动生成有参数构造函数
@Getter                 //输出
@Entity
public class Member {
    @Id  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)//不可更新
    private Long id;
    @Column(name="name",nullable=false)  //不可为空
    private String name;

    public Member(String name){
        this.name=name;
    }
    public void setName(String name){
        this.name=name;
    }
}
