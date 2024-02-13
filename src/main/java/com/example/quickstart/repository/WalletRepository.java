package com.example.quickstart.repository;

import com.example.quickstart.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

}
