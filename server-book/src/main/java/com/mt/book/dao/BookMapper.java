package com.mt.book.dao;

import com.mt.common.core.SysBaseMapper;
import com.mt.common.entity.book.BookEntity;

import java.util.List;

/**
 * @auther: motb
 * @date: 2020/4/27 17:18
 * @description:
 */
public interface BookMapper extends SysBaseMapper<BookEntity> {

    List<BookEntity> selectByList(BookEntity bookEntity);
}
