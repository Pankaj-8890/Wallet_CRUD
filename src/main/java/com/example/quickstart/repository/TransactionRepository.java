package com.example.quickstart.repository;

import com.example.quickstart.models.Transaction;
import com.example.quickstart.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT th.* FROM public.transaction th WHERE th.username = ?1", nativeQuery = true)
    List<Transaction> findByCurrentUser(String username);


}