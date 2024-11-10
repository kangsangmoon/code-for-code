package com.codeforcode.example.repository;

import com.codeforcode.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExampleRepository  extends JpaRepository<Example, Long>, JpaSpecificationExecutor<Example> {
    Optional<Example> findBySolutionId(Long solutionId);
}