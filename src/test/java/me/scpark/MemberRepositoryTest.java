package me.scpark;

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
        //중비(given)

        //실행(when)
        List<Member> members = memberRepository.findAll();//select * from member;

        //검증(then)
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository 를 통해 이름이 'C'인 레코드 가져오기")
    @Test
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
        //중비(given)

        //실행(when)
        Member member = memberRepository.findById(2L).get();

        //검증(then)
        assertThat(member.getName()).isEqualTo("B");

    }
    @DisplayName("MemberRepository를 이름이 '박성철'인 레코드 추가하기")
    @Test
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
}