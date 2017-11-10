package com.nevex.dao;

import com.nevex.dao.entity.VotingInstanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
@Repository
public interface VotingInstancesRepository extends CrudRepository<VotingInstanceEntity, Integer> {

    Optional<VotingInstanceEntity> findOneByResourceName(String resourceName);
}
