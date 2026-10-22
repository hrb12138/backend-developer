package me.scpark;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;


    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 member 테이블의 모든 레코드(3개) 가져오기.")
    @Test
    public void getAllMembers(){
        //중비(given)

        //실행(when)
        List<Member> members = memberRepository.findAll();//select * from member;

        //검증(then)
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository 를 통해 이름이 'C'인 레코드 가져오기")
    @Test
    public void getMemberByName(){
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
    public void getMemberById(){
        //중비(given)

        //실행(when)
        Member member = memberRepository.findById(2L).get();

        //검증(then)
        assertThat(member.getName()).isEqualTo("B");

    }

    @DisplayName("MemberRepository를 이름이 '박성철'인 레코드 추가하기")
    @Test
    public void saveMember(){
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
    @DisplayName("delete")
    @Test
    public void deleteById3(){
        //given




        //when
        memberRepository.deleteById(3L);


        //then
        List<Member> member=memberRepository.findAll();
        assertThat(member.size()).isEqualTo(2);

    }
    @Sql("/insert-members.sql")
    @DisplayName("테이블 member의 모든 레코드 삭제하기")
    @Test
    public void deleteAll() {
        // 준비 given
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(3);


        // 실행 when
        memberRepository.deleteAll();

        // 검증 then
        // member 테이블에서 모든 레코드를 가져왔더니 하나도 없더라.
        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isZero();
    }

    @DisplayName("테이블 member name이 'A', 'B'인 레코드를 한 번에 삽입하기")
    @Test
    public void saveMembers() {
        // 준비 given

        List<Member> members = List.of(new Member("A"),new Member("B"));

        // 실행 when
        memberRepository.saveAll(members);

        // 검증 then
        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 통해 Id가 2인 레코드의 이름을 'B', 'BB'로 변경하기")
    @Test
    public void update(){
        Member member = memberRepository.findById(2L).orElseThrow();

        // 실행 when
        member.setName("BB");

        memberRepository.flush();

        // 검증 then
        Member result = memberRepository.findById(2L).orElseThrow();
        assertThat(result.getName()).isEqualTo("BB");


    }
}
