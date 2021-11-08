package com.example.criptopriceobtainer.repository;

import com.example.criptopriceobtainer.model.BitcoinInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BitcoinRepository extends CrudRepository<BitcoinInfo, Long> {

    Optional<List<BitcoinInfo>> findByTimestampBetween(LocalDateTime from, LocalDateTime to);

    Optional<BitcoinInfo> findByTimestampEquals(LocalDateTime timestamp);
}
