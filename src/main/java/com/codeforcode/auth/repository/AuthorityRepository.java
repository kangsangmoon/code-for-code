package com.codeforcode.auth.repository;

import com.codeforcode.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}