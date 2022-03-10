package org.example.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransferRepository {
    public void writeTransferTransaction();
}
