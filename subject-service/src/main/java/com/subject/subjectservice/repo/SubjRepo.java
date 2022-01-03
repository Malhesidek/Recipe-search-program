package com.subject.subjectservice.repo;

import com.subject.subjectservice.repo.model.Subj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjRepo extends JpaRepository<Subj, Long> {
}
