package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.dto.ZoneDTO;
import org.jesusyouth.jykc.jykcadmin.model.Zone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ZoneRepo extends CrudRepository<Zone,Integer> {
    @Query(value = "select zone.id as zoneID,zone.name as zoneName,users.name as userName,users.email as userMail,users.phone from users inner join zone on zone.id= users.zone WHERE users.role LIKE '%zonal_admin%'",nativeQuery = true)
    List<ZoneDTO> getZoneDetails();
}
