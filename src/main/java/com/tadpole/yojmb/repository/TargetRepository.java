package com.tadpole.yojmb.repository;

import com.tadpole.yojmb.domain.Target;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Target entity.
 */
public interface TargetRepository extends JpaRepository<Target,Long> {

}
