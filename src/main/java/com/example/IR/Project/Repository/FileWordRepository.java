package com.example.IR.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IR.Project.Model.FileWord;


@Repository
public interface FileWordRepository extends JpaRepository<FileWord,Integer>{

}
