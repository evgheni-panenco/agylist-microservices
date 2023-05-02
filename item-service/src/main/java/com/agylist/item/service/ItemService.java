package com.agylist.item.service;

import com.agylist.item.dto.ItemRequest;
import com.agylist.item.dto.UpdateItemRequest;
import com.agylist.item.exception.NotEligibleException;
import com.agylist.item.exception.ResourceNotFoundException;
import com.agylist.item.integration.ProjectServiceClient;
import com.agylist.item.integration.SprintServiceClient;
import com.agylist.item.integration.UserServiceClient;
import com.agylist.item.model.Item;
import com.agylist.item.model.Status;
import com.agylist.item.repository.ItemRepository;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ProjectServiceClient projectServiceClient;
    private final SprintServiceClient sprintServiceClient;
    private final UserServiceClient userServiceClient;

    public List<Item> getItemsByProject(UUID projectId, String username, Integer page) {
        isProjectMember(username, projectId);

        return itemRepository.findAllByProjectId(projectId, PageRequest.of(page, 10));
    }

    public List<Item> getItemsBySprint(UUID sprintId, String username) {
        val sprint = sprintServiceClient.getSprintById(sprintId, username);
        isProjectMember(username, sprint.getProjectId());

        return itemRepository.findAllBySprintId(sprintId);
    }

    public Item createNewItem(ItemRequest itemRequest, String username) {
        val projectId = itemRequest.getProjectId();
        val userId = isProjectMember(username, projectId);

        val item = Item.builder()
                .id(UUID.randomUUID())
                .status(Status.TODO)
                .projectId(projectId)
                .title(itemRequest.getTitle())
                .storyPoints(itemRequest.getStoryPoints())
                .description(itemRequest.getDescription())
                .reporterUserId(userId)
                .createdOn(OffsetDateTime.now())
                .build();

        if (itemRequest.getSprintId() != null) {
            val sprint = sprintServiceClient.getSprintById(itemRequest.getSprintId(), username);
            if (sprint != null) {
                item.setSprintId(sprint.getId());
            }
        }

        return itemRepository.save(item);
    }

    public Item updateItem(UpdateItemRequest request, UUID itemId, String username) {

        val item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        val projectId = item.getProjectId();
        isProjectMember(username, projectId);

        item.setStatus(request.getStatus());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setStoryPoints(request.getStoryPoints());

        if (request.getAssignedUserId() != null) {
            val user = userServiceClient.getUserById(request.getAssignedUserId());
            val projectUser = projectServiceClient.getProjectUser(item.getProjectId(),
                    user.getUsername());

            if (projectUser != null) {
                item.setAssignedUserId(request.getAssignedUserId());
            }
        } else {
            item.setAssignedUserId(null);
        }

        if (request.getSprintId() != null) {
            val sprint = sprintServiceClient.getSprintById(request.getSprintId(), username);
            if (sprint != null && sprint.getProjectId().equals(item.getProjectId())) {
                item.setSprintId(request.getSprintId());
            }
        } else {
            item.setSprintId(null);
        }

        itemRepository.save(item);

        return item;
    }

    public Item getItem(UUID itemId, String username) {
        val item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        val projectId = item.getProjectId();
        isProjectMember(username, projectId);

        return item;
    }

    private UUID isProjectMember(String username, UUID projectId) {
        val projectUser = projectServiceClient.getProjectUser(projectId, username);
        if (projectUser == null) {
            throw new NotEligibleException("User is not part of the project");
        }
        return projectUser.getUserId();
    }
}
