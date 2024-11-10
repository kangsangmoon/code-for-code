package com.codeforcode.solution.domain.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Topic {
    GREED("탐욕 알고리즘"),
    DYNAMIC("동적 알고리즘"),



    ;
    
    private final String topic;

    public Topic getCorrectTopic(String topic) {
        return Topic.valueOf(topic);
    }
}
