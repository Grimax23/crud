package com.example.crud.repository;

import com.example.crud.model.Document;
import com.example.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>{

    List<Document> findAllByOwnerAndExpiryDateGreaterThan(User owner, LocalDate localDate);

}
