package com.example.quickstart.repository;

import com.example.quickstart.models.Transaction;
import com.example.quickstart.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}