package com.example.quickstart.repository;

import com.example.quickstart.models.WalletModel;
import com.example.quickstart.models.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Integer> {

}
