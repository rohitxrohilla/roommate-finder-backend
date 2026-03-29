package com.roommatefinder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityResponse {
    private Long user1Id;
    private Long user2Id;
    private Integer compatibilityScore;
    private Map<String, Object> breakdown;
    private String summary;
    private String recommendation;
}