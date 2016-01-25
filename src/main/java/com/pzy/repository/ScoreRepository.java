package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.pzy.entity.Score;
public interface ScoreRepository extends PagingAndSortingRepository<Score, Long>,JpaSpecificationExecutor<Score>{
}

