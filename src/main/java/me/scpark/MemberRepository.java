package me.scpark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>{
    //findAll,findById,save,deleteById,deleteAll,...
    //select * from member;
    //select * from member where id = 2;
    //insert into ...,update member...
    //delect from member where id =?...
    //delect from member;

    //select * from member where name = ?
    public Optional<Member> findByName(String name);
}
