package com.Ayush.TodoList.repository;

import com.Ayush.TodoList.model.ListItem;
import com.Ayush.TodoList.model.ListItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface ListItemRepository extends JpaRepository<ListItem, ListItemPK> {

    @Query(value = "SELECT * FROM listItem WHERE userName = :userName AND itemId = :itemId", nativeQuery = true)
    Optional<ListItem> findByKey(String userName, String itemId);

    @Query(value = "SELECT * FROM listItem WHERE userName = :userName", nativeQuery = true)
    Optional<ArrayList<ListItem>> findByUserName(String userName);
}
