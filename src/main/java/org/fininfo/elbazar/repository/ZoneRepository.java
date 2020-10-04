package org.fininfo.elbazar.repository;

import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.User;
import org.fininfo.elbazar.domain.Zone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Zone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>, JpaSpecificationExecutor<Zone> {
	
	Optional<Zone> findOneByCodeZone(String codeZone);
	
	
}
