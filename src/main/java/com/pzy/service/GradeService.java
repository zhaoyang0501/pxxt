
package com.pzy.service;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzy.entity.Grade;
import com.pzy.repository.GradeRepository;
import com.pzy.repository.ReportRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Component
@Service
public class GradeService {
     @Autowired
     private GradeRepository gradeRepository;
     @Autowired
     private ReportRepository reportRepository;

 	public List<Grade> findTop3() {
 		return gradeRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Grade> findAll() {
    	 List<Grade> grades=(List<Grade> ) gradeRepository.findAll(new Sort(Direction.DESC, "id"));
    	 for(Grade grade: grades){
    		 grade.setUsernum(reportRepository.findByGrade(grade).size());
    	 }
    	 return grades;
     }
     public Page<Grade> findAll(final int pageNumber, final int pageSize,final String name){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Grade> spec = new Specification<Grade>() {
              public Predicate toPredicate(Root<Grade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Grade> result = (Page<Grade>) gradeRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Grade> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Grade> spec = new Specification<Grade>() {
              public Predicate toPredicate(Root<Grade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Grade> result = (Page<Grade>) gradeRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			gradeRepository.delete(id);
		}
		public Grade find(Long id){
			  return gradeRepository.findOne(id);
		}
		public void save(Grade grade){
			gradeRepository.save(grade);
		}
		
		
		public void AutoEndGrade(){
			System.out.println("fuckyou!----");
			 List<Grade> grades=(List<Grade> ) gradeRepository.findAll(new Sort(Direction.DESC, "id"));
	    	 for(Grade grade: grades){
	    		 grade.setUsernum(reportRepository.findByGrade(grade).size());
	    	 }
	    	 
	    	 for(Grade grade: grades){
	    		if(grade.getUsernum()>=30||grade.getEnd().before(new Date(System.currentTimeMillis()))){
	    			grade.setState("报名结束");
	    			grade.setCreateDate(new Date(System.currentTimeMillis()));
	    			this.save(grade);
	    		}
	    	 }
	    	 System.out.println("fuckyouend!----");
		}
}