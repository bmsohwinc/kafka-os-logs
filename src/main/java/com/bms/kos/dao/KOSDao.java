package com.bms.kos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.kos.domain.ParsedOSLogEntry;

@Repository
public interface KOSDao extends JpaRepository<ParsedOSLogEntry, Long> {   
}
