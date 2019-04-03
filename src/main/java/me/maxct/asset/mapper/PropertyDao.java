package me.maxct.asset.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Property;

/**
 * @author imaxct
 * 2019-03-21 13:03
 */
@Repository
public interface PropertyDao extends JpaRepository<Property, Long> {
    Optional<Property> findByPropertyId(String propertyId);

    List<Property> findByOccupyUserId(Long occupyUserId);

    List<Property> findByDepId(Long depId);
}
