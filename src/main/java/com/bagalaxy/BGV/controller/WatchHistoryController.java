package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.WatchHistoryDTO;
import com.bagalaxy.BGV.form.watchhistory.WatchHistoryForm;
import com.bagalaxy.BGV.service.WatchHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watch-history")
@RequiredArgsConstructor
public class WatchHistoryController {
    private final WatchHistoryService watchHistoryService;

    @PostMapping
    public ResponseEntity<WatchHistoryDTO> addOrUpdate(@RequestParam Long userId, @RequestBody @Valid WatchHistoryForm form) {
        return ResponseEntity.ok(watchHistoryService.addOrUpdate(userId, form));
    }

    @GetMapping
    public ResponseEntity<List<WatchHistoryDTO>> getByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(watchHistoryService.getByUserId(userId));
    }
}