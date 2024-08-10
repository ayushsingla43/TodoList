package com.Ayush.TodoList.controller;

import com.Ayush.TodoList.dto.ListItemDTO;
import com.Ayush.TodoList.service.ListItemService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Slf4j
public class ListItemController {

    @Autowired
    private final ListItemService listItemService;

    @GetMapping("/{userName}/{itemId}")
    public ResponseEntity<ListItemDTO> getListItem(@PathVariable String userName, @PathVariable String itemId){
        log.info("Call made to - \"GET/api/item/{}/{}\"", userName, itemId);
        ListItemDTO listItemDTO = listItemService.getListItem(userName, itemId);
        if(listItemDTO != null) return ResponseEntity.ok(listItemDTO);
        else return new ResponseEntity<>((ListItemDTO) null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userName}")
    public ResponseEntity getAllListItems(@PathVariable String userName){
        log.info("Call made to - \"GET/api/item/{}\"", userName);
       try{
           ArrayList<ListItemDTO> listItemDTOS = listItemService.getAllListItems(userName);
           return ResponseEntity.ok(listItemDTOS);
       } catch (Exception E){
           log.error(E.getMessage());
           return new ResponseEntity<>(E.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/{userName}/{itemId}")
    public ResponseEntity<String> upsertListItem(@PathVariable String userName, @PathVariable String itemId ,@RequestBody JsonNode payload) {
        log.info("Call made to - \"POST/api/{}/{}\"", userName, itemId);
        ((ObjectNode)payload).put("userName", userName);
        ((ObjectNode)payload).put("itemId", itemId);
        String response = listItemService.upsertListItem(payload);
        if(response.equals("Success")) return ResponseEntity.ok(response);
        else return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{userName}/{itemId}")
    public ResponseEntity<String> deleteListItem(@PathVariable String userName, @PathVariable String itemId){
        log.info("Call made to - \"DELETE/api/{}/{}\"", userName, itemId);
        String response = listItemService.deleteListItem(userName, itemId);
        return Objects.equals(response, "Success") ? ResponseEntity.ok(response) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
