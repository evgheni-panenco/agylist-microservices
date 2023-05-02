package com.agylist.item.controller;

import com.agylist.item.dto.ItemRequest;
import com.agylist.item.dto.UpdateItemRequest;
import com.agylist.item.model.Item;
import com.agylist.item.service.ItemService;
import jakarta.ws.rs.HeaderParam;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> createNewItem(@RequestBody ItemRequest request,
                                              @RequestHeader String username) {
        val response = itemService.createNewItem(request, username);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@RequestBody UpdateItemRequest request,
                                           @PathVariable UUID itemId,
                                           @RequestHeader String username) {
        val item = itemService.updateItem(request, itemId, username);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable UUID itemId, @RequestHeader String username) {
        val item = itemService.getItem(itemId, username);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Item>> getAllTimesByProject(@PathVariable UUID projectId,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestHeader String username) {
        val response = itemService.getItemsByProject(projectId, username, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<Item>> getAllTimesBySprint(@PathVariable UUID sprintId,
                                                  @RequestHeader String username) {
        val response = itemService.getItemsBySprint(sprintId, username);
        return ResponseEntity.ok(response);
    }
}
