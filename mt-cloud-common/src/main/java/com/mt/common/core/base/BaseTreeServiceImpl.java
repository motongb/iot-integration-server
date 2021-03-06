package com.mt.common.core.base;

import com.mt.common.core.SysBaseMapper;
import com.mt.common.core.TreeBuilder;
import com.mt.common.utils.UUIDUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author motb
 * @date 2020/4/13 16:59
 * @description
 */
@Slf4j
@Getter
public abstract class BaseTreeServiceImpl<T extends BaseTreeEntity, M extends SysBaseMapper<T>> extends BaseServiceImpl<T, M> implements BaseTreeService<T, M> {

    @Override
    public T save(T t) {
        if (StringUtils.hasText(t.getParentTreeId())) {
            try {
                BaseTreeEntity query = t.getClass().newInstance();
                query.setTreeId(t.getParentTreeId());
                T parent = getBaseMapper().selectOne((T) query);
                Assert.notNull(parent, "parentTreeId is invalid");
                handleParentProperties(parent, t);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        t.setTreeId(UUIDUtils.UUID());
        return super.save(t);
    }

    @Override
    public List<T> tree() {
        return TreeBuilder.build(getBaseMapper().selectAll());
    }


    /**
     * 处理父级属性
     *
     * @param parent
     * @param treeEntity
     */
    private void handleParentProperties(BaseTreeEntity parent, BaseTreeEntity treeEntity) {
        if (StringUtils.isEmpty(parent.getParentTreeIds())) {
            treeEntity.setParentTreeIds(treeEntity.getParentTreeId());
            treeEntity.setParentNames(parent.getName());
        } else {
            treeEntity.setParentTreeIds(parent.getParentTreeIds() + "," + treeEntity.getParentTreeId());
            treeEntity.setParentNames(parent.getParentNames() + "/" + parent.getName());
        }
    }
}
