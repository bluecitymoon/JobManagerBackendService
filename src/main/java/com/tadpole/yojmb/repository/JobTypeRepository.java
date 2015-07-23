package com.tadpole.yojmb.repository;

import com.tadpole.yojmb.domain.JobType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobType entity.
 */
public interface JobTypeRepository extends JpaRepository<JobType,Long> {

}
