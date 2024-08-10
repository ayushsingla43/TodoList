package com.Ayush.TodoList.model;

import com.Ayush.TodoList.common.Enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
@IdClass(ListItemPK.class)
@Table(name = "listItem")
@AllArgsConstructor
@NoArgsConstructor
public class ListItem {

    @Id
    private String userName;

    @Id
    private String itemId;
    private String header;
    private ArrayList<String> description;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Enumerated(EnumType.STRING)
    private Status status;
}
