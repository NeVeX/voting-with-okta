package com.nevex.dao;

import com.nevex.dao.entity.VotingInstanceEntity;
import com.nevex.dao.entity.VotingInstanceInformationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
@Repository
public interface VotingInstanceInformationRepository extends CrudRepository<VotingInstanceInformationEntity, Integer> {

    List<VotingInstanceInformationEntity> findAllByVotingId(int votingId);

    Optional<VotingInstanceInformationEntity> findOneByIdAndVotingId(int id, int votingId);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteAllByVotingId(int votingId);

}
