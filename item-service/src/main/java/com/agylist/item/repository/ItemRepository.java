package com.agylist.item.repository;

import com.agylist.item.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findAllByProjectId(UUID projectId, Pageable pageable);
    List<Item> findAllBySprintId(UUID projectId);
}
