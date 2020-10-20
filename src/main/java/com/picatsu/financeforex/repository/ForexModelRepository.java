package com.picatsu.financeforex.repository;

import com.picatsu.financeforex.model.ForexModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ForexModelRepository extends MongoRepository<ForexModel, String> {

    List<ForexModel> findByCodeContainsIgnoreCase(String str);
    List<ForexModel> findByNameContainsIgnoreCase(String str);
    boolean deleteAllByCode(String code);
}
