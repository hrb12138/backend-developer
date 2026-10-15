package me.scpark;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;


    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 member 테이블의 모든 레코드(3개) 가져오기.")
    @Test
    public void getAllMembers(){//查找数量 如果正确这测试通过
        //중비(given)

        //실행(when)
        List<Member> members = memberRepository.findAll();//select * from member;

        //검증(then)
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository 를 통해 이름이 'C'인 레코드 가져오기")
    @Test
    public void getMemberByName(){//通过name查找主键 如果符合则通过
        //중비(given)

        //실행(when)
        Optional<Member> optMember=memberRepository.findByName("C");
        Member member = optMember.get();


        //검증(then)
        assertThat(member.getId()).isEqualTo(3);
    }

    //select * from
    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 Id가 2인 레코드 가져오기")
    @Test
    public void getMemberById(){//通过主键查找name 如果符合则通过
        //중비(given)

        //실행(when)
        Member member = memberRepository.findById(2L).get();

        //검증(then)
        assertThat(member.getName()).isEqualTo("B");

    }
    @DisplayName("MemberRepository를 이름이 '박성철'인 레코드 추가하기")
    @Test
    public void saveMember(){//添加新数据
        //중비(given):Member 객체를 생성
        //Member member = new Member(1L,"박성철");
        Member member = new Member("박성철");

        //실행(when)
        memberRepository.save(member);

        //검증(then)
        //member 테이블에서 모든 레코드를 select 했을 때 레코드의 수가 1이고
        //그 레코드의 name 칼럼 값이 '박성철'인지 확인
        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("박성철");
    }

    @Sql("/insert-members.sql")
    @DisplayName("테이블 member 에서 id가 3인 레코드 삭제하기")
    @Test
    public void deleteById3(){//删除后测试数量是否-1
        //중비(given)

        //실행(when)
        memberRepository.deleteById(3L);

        //검증(then)
        List<Member> members= memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
    @Sql("/insert-members.sql")
    @DisplayName("테이블 member의 모든 레코드 삭제하기")
    @Test
    public void deleteAll(){
        //중비(given)
        List<Member> members=memberRepository.findAll();
        assertThat(members.size()).isEqualTo(3);
        //실행(when)
        memberRepository.deleteAll();
        //검증(then)->member 테이블에서 모든 레코드를 가져왔더니 하나도 없더라
        members=memberRepository.findAll();
        assertThat(members.size()).isEqualTo(0);//isZero()
    }

    @DisplayName("테이블 member name이 'A','B'인 레코드를 한번에 삽입하기")
    @Test
    public void saveMembers(){
        //중비(given)
        //두 개의 Member 객체를 생성해서 List에 넣는다
        List<Member> members = List.of(new Member("A"),new Member("B"));

        //실행(when)
        memberRepository.saveAll(members);

        //검증(then)
        //테이블에서 모든 레코드를 selection 했더니 갯수가 2개임을 검증
        members=memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 통해 Id가 2인 레코드의 이름을 'B','BB'로 변경하기")
    //@Transactional   (@DataJpaTest里已包含)
    @Test
    public void update(){
        //중비(given)
        Member member=memberRepository.findById(2L).get();//findById()提供optional

        //실행(when)
        member.setName("BB");
        //memberRepository.save(member);

        //검증(then)
        member=memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("BB");
    }
}