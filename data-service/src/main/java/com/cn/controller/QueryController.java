package com.cn.controller;

import com.cn.util.R;
import com.cn.entity.Score;
import com.cn.service.QueryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("query")
public class QueryController {

    @Resource
    private QueryService queryService;

    @GetMapping("{id}")
    public R getScoreById(@PathVariable Integer id) {
        return queryService.selectScoreById(id);
    }

    @GetMapping("/get/{id}")
    public R selectById(@PathVariable Integer id) {
        return queryService.selectById(id);
    }

    @GetMapping()
    public R getScore() {
        return queryService.selectScoreList();
    }

    @PutMapping("/update")
    public R updateScore(@RequestBody Score score) {
        return queryService.updateScore(score);
    }

    @DeleteMapping("/del/{id}")
    public R delScoreById(@PathVariable Integer id) {
        return queryService.deleteScoreById(id);
    }
}
