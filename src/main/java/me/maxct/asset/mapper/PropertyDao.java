package me.maxct.asset.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Property;
import me.maxct.asset.dto.PropertySimpleVO;

/**
 * @author imaxct
 * 2019-03-21 13:03
 */
@Repository
public interface PropertyDao extends JpaRepository<Property, Long> {
    Optional<Property> findByPropertyId(String propertyId);

    List<Property> findByOccupyUserId(Long occupyUserId);

    List<Property> findByDepId(Long depId);

    @Query("select new me.maxct.asset.dto.PropertySimpleVO(p.id, p.name, p.curStatus, p.propertyId, "
           + "process.name, u.id, u.name, d.id, d.name, p.gmtCreate, p.gmtModified) "
           + "from Property p left join User u on p.occupyUserId = u.id "
           + "left join Department d on p.depId = d.id left join Process process on p.processId = process.id")
    Page<PropertySimpleVO> list(Pageable pageable);
}
