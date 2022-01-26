package com.azurealstn.springbootcodeweb.domain.sample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
