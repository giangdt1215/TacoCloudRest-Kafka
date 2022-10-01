package com.dtg.tacocloud.data.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dtg.tacocloud.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>{

}
