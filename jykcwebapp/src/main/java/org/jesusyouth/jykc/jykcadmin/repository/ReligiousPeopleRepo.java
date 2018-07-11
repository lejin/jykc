package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.model.ReligiousPeople;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReligiousPeopleRepo extends CrudRepository<ReligiousPeople, Integer> {
    List<ReligiousPeople> findAllByZoneEquals(Integer zone);
}
