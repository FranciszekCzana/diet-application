package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsqlUserRepository extends JpaRepository<PsqlUser, Long> {

}