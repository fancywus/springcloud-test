package com.cn.service;

import com.cn.util.R;
import com.cn.entity.Score;

public interface QueryService {

    // Score selectScoreAndUserScore(Integer id);
    //
    // Score selectScoreScore(Integer id);

    R deleteScoreById(Integer id);

    R updateScore(Score score);

    R selectScoreById(Integer id);

    R selectScoreList();

    R selectById(Integer id);
}
