package com.zendaimoney.thirdpp.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <p>分页器。用于分页查询，封装页号、分页大小、总记录数、当页数据等信息。</p>
 * <p>关于是否有下一页：参见{@link Page#isHasNextPage()} </p>
 *
 * @param <T> 数据类型
 * @since 1.0
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -7474285477052418248L;
    /**
     * 当前页号
     */
    private Integer pageNo;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 记录总数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer pageCount;
    /**
     * 是否有下一页。
     */
    private Boolean hasNextPage;
    /**
     * 当页数据
     */
    private List<T> data = Collections.emptyList();

    /**
     * 创建空的分页器对象，未设置任何属性。
     */
    public Page() {
        super();
    }

    /**
     * 创建分页器对象，数据对象为空集合。
     * 用于未查询到结果的情况。
     *
     * @param pageNo     页号
     * @param pageSize   分页大小
     * @param totalCount 总记录数
     */
    public Page(Integer pageNo, Integer pageSize, Integer totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    /**
     * 创建分页器对象
     *
     * @param pageNo     页号
     * @param pageSize   分页大小
     * @param totalCount 总记录数
     * @param data       当页数据
     */
    public Page(Integer pageNo, Integer pageSize, Integer totalCount, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
    }

    /**
     * 创建分页器对象
     *
     * @param pageNo   页号
     * @param pageSize 分页大小
     * @param data     当页数据
     */
    public Page(Integer pageNo, Integer pageSize, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.data = data;
    }

    /**
     * 创建分页器对象
     *
     * @param pageNo      页号
     * @param pageSize    分页大小
     * @param hasNextPage 是否有下一页
     * @param data        当页数据
     */
    public Page(Integer pageNo, Integer pageSize, Boolean hasNextPage, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.hasNextPage = hasNextPage;
        this.data = data;
    }

    /**
     * 计算某一页从第多少条数据开始。第一页从0开始。
     *
     * @param pageNo   页号
     * @param pageSize 分页大小
     * @return 某一页从第多少条数据开始
     */
    public static Integer getFirstResult(Integer pageNo, Integer pageSize) {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 计算某一页从第多少条数据开始。第一页从0开始。
     *
     * @return 某一页从第多少条数据开始
     */
    public Integer getFirstResult(Integer page) {
        return getFirstResult(page, pageSize);
    }

    /**
     * 得到页号
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 得到分页大小
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 得到总记录数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 如果设置过直接返回，否则计算是否有下一页。
     *
     * @return <p>1.数据为空，返回false。</p>
     * <p>2.总页数大于当前页，返回true，否则返回false。</p>
     * <p>3.当前页的数据大小大于分页大小，返回true，否则返回false</p>
     */
    public Boolean isHasNextPage() {
        if (hasNextPage != null) {
            return hasNextPage;
        }
        if (data == null || data.isEmpty()) {
            this.hasNextPage = false;
        } else if (getPageCount() != null) {
            if (pageCount > pageNo) {
                this.hasNextPage = true;
            } else {
                this.hasNextPage = false;
            }
        } else if (data.size() > pageSize) {
            this.hasNextPage = true;
        } else {
            this.hasNextPage = false;
        }
        return hasNextPage;
    }

    /**
     * 得到当页数据
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 获取总页数。
     * 如果设置过总页数直接返回，未设置过总页数通过总记录数和分页大小计算。
     *
     * @return 总页数，如无法计算(总记录数或者分页大小为null)，返回null
     */
    public Integer getPageCount() {
        if (pageCount != null) {
            return pageCount;
        }
        if (totalCount == null || pageSize == null) {
            return null;
        }
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = (totalCount / pageSize) + 1;
        }
        return pageCount;
    }

    /**
     * 设置总页数
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 设置分页大小
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 设置总页数
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 设置是否有下一页
     */
    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    /**
     * 设置当页数据
     */
    public void setData(List<T> data) {
        this.data = data;
    }

}
