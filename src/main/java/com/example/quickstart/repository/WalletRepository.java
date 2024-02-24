package com.example.quickstart.repository;

import com.example.quickstart.models.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel,Long> {

}
