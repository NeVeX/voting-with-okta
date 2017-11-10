package com.nevex.dao;

import com.nevex.dao.entity.VoteEntity;
import com.nevex.dao.entity.VotingInstanceInformationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {

    Optional<VoteEntity> findOneByVotingIdAndUsername(int votingId, String username);
}
