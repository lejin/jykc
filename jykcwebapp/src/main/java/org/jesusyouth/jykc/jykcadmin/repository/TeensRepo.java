package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.model.Teen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeensRepo extends CrudRepository<Teen,Integer> {

    @Transactional
    Long deleteTeenByTeenIdEquals(Integer teenId);

    List<Teen> findAllByTeenGroupIdEquals(Integer groupId);
}
