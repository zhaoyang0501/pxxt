package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.pzy.entity.Report;
public interface ReportRepository extends PagingAndSortingRepository<Report, Long>,JpaSpecificationExecutor<Report>{
}

