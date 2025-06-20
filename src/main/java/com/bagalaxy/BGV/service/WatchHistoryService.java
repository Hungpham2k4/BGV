package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.WatchHistoryDTO;
import com.bagalaxy.BGV.form.watchhistory.WatchHistoryForm;

import java.util.List;

public interface WatchHistoryService {
    List<WatchHistoryDTO> getByUserId(Long userId);

    WatchHistoryDTO addOrUpdate(Long userId, WatchHistoryForm form);

    void delete(Long id);
}
