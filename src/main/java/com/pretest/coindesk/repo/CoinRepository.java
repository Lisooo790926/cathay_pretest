package com.pretest.coindesk.repo;

import com.pretest.coindesk.models.CoinModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<CoinModel, String> {

}
