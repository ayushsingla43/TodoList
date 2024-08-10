package com.Ayush.TodoList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListItemPK implements Serializable {
    private String userName;
    private String itemId;

    @Override
    public int hashCode() {
        return Objects.hash(userName, itemId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof ListItemPK)) return false;
        return Objects.equals(userName, ((ListItemPK) obj).userName) &&
                Objects.equals(itemId, ((ListItemPK) obj).itemId);
    }
}
