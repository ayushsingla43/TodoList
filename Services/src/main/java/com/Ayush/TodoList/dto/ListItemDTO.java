package com.Ayush.TodoList.dto;

import com.Ayush.TodoList.common.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListItemDTO {

    private String userName;
    private String itemId;
    private ArrayList<String> description;
    private String header;
    private Date deadline;
    private Status status;
}
