package com.mt.common.core;

import com.mt.common.core.base.BaseTreeEntity;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author motb
 * @date 2019/10/26 17:19
 * @description
 */
public class TreeBuilder<T extends BaseTreeEntity> {

    public static <T> List<T> build(List treeNodes) {
        return new TreeBuilder<>().self(treeNodes);
    }

    private List<T> self(List<T> treeNodes) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (StringUtils.isEmpty(treeNode.getParentTreeId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (StringUtils.hasText(it.getParentTreeId()) && it.getParentTreeId().equals(treeNode.getTreeId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }
}
