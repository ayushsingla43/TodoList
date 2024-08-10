package com.Ayush.TodoList.service;

import com.Ayush.TodoList.common.Enums.Status;
import com.Ayush.TodoList.dto.ListItemDTO;
import com.Ayush.TodoList.model.ListItem;
import com.Ayush.TodoList.repository.ListItemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListItemService {

    @Autowired
    private final ListItemRepository listItemRepository;

    public ListItemDTO getListItem(String userName, String itemId){
        try{
            Optional<ListItem> optionalListItem = listItemRepository.findByKey(userName, itemId);
            if(optionalListItem.isEmpty()) return null;
            ListItem listItem = optionalListItem.get();
            log.info("Found listItem - populating into DTO");
            return new ListItemDTO(listItem.getUserName(), listItem.getItemId(),
                    listItem.getDescription(), listItem.getHeader(), listItem.getDeadline(), listItem.getStatus());
        } catch (Exception E){
            log.error(E.getMessage());
            return null;
        }
    }

    public ArrayList<ListItemDTO> getAllListItems(String userName){
        Optional<ArrayList<ListItem>> optionalListItems = listItemRepository.findByUserName(userName);
        if(optionalListItems.isEmpty()) return new ArrayList<>();
        ArrayList<ListItem> arrayList = optionalListItems.get();
        ArrayList<ListItemDTO> arrayList1 = new ArrayList<>();
        for(ListItem listItem : arrayList){
            arrayList1.add(new ListItemDTO(listItem.getUserName(), listItem.getItemId(),
                    listItem.getDescription(), listItem.getHeader(), listItem.getDeadline(), listItem.getStatus()));
        }
        log.info("Found {} many entries", arrayList.size());
        return arrayList1;
    }

    public String upsertListItem(JsonNode payload){
        try {
            ListItem listItem = new ListItem();
            listItem.setUserName(payload.get("userName").asText());
            listItem.setItemId(payload.get("itemId").asText());
            if (payload.has("description") && payload.get("description") != null && payload.get("description").isArray()) {
                ArrayList<String> desc = new ArrayList<>();
                for(JsonNode node: payload.get("description")){
                    desc.add(node.asText());
                }
                listItem.setDescription(desc);
            }
            if (payload.has("header") && payload.get("header") != null){
                listItem.setHeader(payload.get("header").asText());
            } else {
                return "Failure: No subject chosen";
            }
            if (payload.has("status") && payload.get("status") != null){
                listItem.setStatus(Status.valueOf(payload.get("status").asText()));
            } else{
                return "Failure: No status set";
            }
            if (payload.has("deadline") && payload.get("deadline") != null){
                listItem.setDeadline(new SimpleDateFormat("dd-MM-yyyy").parse(payload.get("deadline").asText()));
            }
            listItemRepository.save(listItem);
            log.info("Successfully saved the listItem into the DB");
            return "Success";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Failure: " + e.getMessage();
        }

    }

    public String deleteListItem(String userName, String itemId){
        try{
            Optional<ListItem> optionalListItem = listItemRepository.findByKey(userName, itemId);
            if(optionalListItem.isEmpty()) return "Success";
            ListItem listItem = optionalListItem.get();
            listItemRepository.delete(listItem);
            log.info("Successfully deleted {}-{} from the DB", userName, itemId);
            return "Success";
        } catch (Exception E){
            log.error(E.getMessage());
            return "Failure";
        }
    }
}
