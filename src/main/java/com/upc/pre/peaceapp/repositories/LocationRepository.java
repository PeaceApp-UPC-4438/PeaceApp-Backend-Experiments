package com.upc.pre.peaceapp.repositories;

import com.upc.pre.peaceapp.models.Location;
import com.upc.pre.peaceapp.shared.documentation.models.AuditableAbstractAggregateRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
