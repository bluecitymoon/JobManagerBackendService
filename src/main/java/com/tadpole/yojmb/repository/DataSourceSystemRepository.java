package com.tadpole.yojmb.repository;

import com.tadpole.yojmb.domain.DataSourceSystem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DataSourceSystem entity.
 */
public interface DataSourceSystemRepository extends JpaRepository<DataSourceSystem,Long> {

}
