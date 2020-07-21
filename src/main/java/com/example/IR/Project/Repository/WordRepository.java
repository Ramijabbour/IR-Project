package com.example.IR.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IR.Project.Model.Word;;

@Repository
public interface WordRepository extends JpaRepository<Word,Integer>{

}
