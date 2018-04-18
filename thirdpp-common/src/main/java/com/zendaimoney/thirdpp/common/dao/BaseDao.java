
package com.zendaimoney.thirdpp.common.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于Spring+iBatis实现数据持久化的基础DAO类，需要实现数据持久化的业务对象可继承此类，
 * 并按照规则配置sqlmap文件即可实现数据库的增、删、改、查等操作。
 *
 * <!--
 * 历史记录：
 * ---------------------------------------------------------------------------------------------------------------------
 * -->
 * @version 1.00
 * @since 1.00
 */
@SuppressWarnings("deprecation")
public class BaseDao<T> extends SqlMapClientDaoSupport {

    /**
     * 数据库排序方式-降序
     */
    public final static String SORT_DESC = "DESC";

    /**
     * 数据库排序方式-升序
     */
    public final static String SORT_ASC = "ASC";

    /**
     * 设置数据库排序字段的属性名称
     */
    public final static String ORDER_BY_COLUMNS = "orderColumns";

    /**
     * 设置数据库排序方式的属性名称
     */
    public final static String SORT_TYPES = "sortTypes";

    /**
     * sqlMap文件中，排序的属性名称
     */
    private final static String SQLMAP_ORDERBY = "orderBy";

    /**
     * sqlMap文件中，数据库分页起始行的属性名称
     */
    private final static String SQLMAP_PAGE_LIMIT_START = "start";

    /**
     * sqlMap文件中，数据库分页每页数据行数的属性名称
     */
    private final static String SQLMAP_PAGE_LIMIT_SIZE = "size";

    /**
     * 默认的主键对应的属性名称
     */
    private static final String DEFAULT_ID_NAME = "id";

    /**
     * 注入默认数据源，注入的数据源名称为sqlMapClient，在spring配置文件中设置
     */
    @Resource(name = "sqlMapClient")
    public SqlMapClient sqlMapClient;

    /**
     * 数据源sqlMapClient的初始化
     */
    @PostConstruct
    private void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * DAO所管理的实体类中对应数据库主键的属性名.
     */
    protected String primaryKeyName;

    /**
     * DAO所管理的实体类型.
     */
    protected Class<T> entityClass;

    /**
     * 在构造函数中将泛型T.class赋给entityClass.
     */
    @SuppressWarnings("unchecked")
    public BaseDao() {
        entityClass = (Class<T>) getEntityClass();
        logger.debug("entityClass==" + entityClass);
    }

    /**
     * 向表中插入记录，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <insert id="insert" parameterClass="BankInfo">}</pre>
     */
    public static final String POSTFIX_INSERT = ".insert";

    /**
     * 向表中更新记录，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <update id="update" parameterClass="BankInfo">}</pre>
     */
    public static final String POSTFIX_UPDATE = ".update";

    /**
     * 删除表中记录，以实体类对象作为参数，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <delete id="delete" parameterClass="BankInfo">}</pre>
     */
    public static final String POSTFIX_DELETE = ".delete";

    /**
     * 删除表中记录，以主键值作为参数，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <delete id="deleteByPrimaryKey" parameterClass="String">}</pre>
     */
    public static final String POSTFIX_DELETE_PRIAMARYKEY = ".deleteByPrimaryKey";
    // 根据多个主键删除多记录

    /**
     * 删除表中多个主键对应的记录，以多个主键值拼接的字符串作为参数，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <delete id="deleteByPrimaryKeys" parameterClass="String">}</pre>
     */
    public static final String POSTFIX_DELETE_PRIAMARYKEYS = ".deleteByPrimaryKeys";

    /**
     * 根据主键查询记录，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <select id="select" ...>}</pre>
     */
    public static final String POSTFIX_SELECT = ".select";

    /**
     * 根据条件查询记录总数，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <select id="pageCount" ...>}</pre>
     */
    public static final String POSTFIX_PAGE_COUNT = ".pageCount";

    /**
     * 根据条件分页查询，在sqlmap文件中需要配置的ID名称。
     * 如：<pre>{@code <select id="pageQuery" ...>}</pre>
     */
    public static final String POSTFIX_PAGE_QUERY = ".pageQuery";

    /**
     * 实体类对象保存数据库
     *
     * @param o 要保存的实体类对象
     * @exception DataAccessException 数据库访问异常
     */
    public void insert(Object o) throws DataAccessException {
        getSqlMapClientTemplate().insert(o.getClass().getName() + POSTFIX_INSERT, o);
    }

    /**
     * 将实体类对象更新到数据库
     *
     * @param o 要更新的实体类对象
     * @return 被更新的记录数
     * @exception DataAccessException 数据库访问异常
     */
    public int update(Object o) throws DataAccessException {
        return getSqlMapClientTemplate().update(o.getClass().getName() + POSTFIX_UPDATE, o);
    }

    /**
     * 删除实体类对象对应的数据库记录。
     * 将根据实体类中对应数据库表主键的属性值找到并删除记录
     *
     * @param o 要删除的实体类对象
     * @return 被删除的记录数
     * @exception DataAccessException 数据库访问异常
     */
    public int remove(Object o) throws DataAccessException {
        return getSqlMapClientTemplate().delete(o.getClass().getName() + POSTFIX_DELETE, o);
    }

    /**
     * 根据主键值删除记录
     * @param id 主键值
     * @return 被删除的记录数
     * @exception DataAccessException 数据库访问异常
     */
    public int removeById(Serializable id) throws DataAccessException {
        return removeById(getEntityClass(), id);
    }

    @SuppressWarnings("hiding")
    private <T> int removeById(Class<T> entityClass, Serializable id) throws DataAccessException {
        return getSqlMapClientTemplate().delete(
                entityClass.getName() + POSTFIX_DELETE_PRIAMARYKEY, id);
    }

    /**
     * 删除表中多个主键对应的记录。
     *
     * @param ids 多个主键值拼接的字符串。
     *            主键值用逗号分隔，如下：
     *              <ul>
     *                  <li>字符：'1','2','3',...</li>
     *                  <li>数字：1,2,3,...</li>
     *              </ul>
     *            字符串将作为SQL语句的in条件中：ID in (ids字符串)
     * @return 被删除的记录数
     * @exception DataAccessException 数据库访问异常
     */
    public int removeByIds(String ids) throws DataAccessException {
        return getSqlMapClientTemplate().delete(
                entityClass.getName() + POSTFIX_DELETE_PRIAMARYKEYS, ids);
    }

    /**
     * 根据ID获取对象
     *
     * @param id 主键值
     * @return id 对应的实体对象
     * @exception DataAccessException 数据库访问异常
     */
    public T get(Serializable id) throws DataAccessException {
        if(id == null){
            logger.warn("The input parameter 'id' of get() is null, will return null to caller.");
            return null;
        }
        @SuppressWarnings("unchecked")
        T o = (T) getSqlMapClientTemplate().queryForObject(
                getEntityClass().getName() + POSTFIX_SELECT, id);
        return o;
    }

    @SuppressWarnings({"unchecked", "hiding"})
    private <T> List<T> getAll(Class<T> entityClass) throws DataAccessException {
        return getSqlMapClientTemplate().queryForList(
                entityClass.getName() + POSTFIX_PAGE_QUERY, null);
    }

    /**
     * 获取全部对象
     *
     * @return 全部记录对应的实体对象集合
     * @exception DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() throws DataAccessException {
        return (List<T>) getAll(getEntityClass());
    }

    /**
     * 按条件查询。
     *
     * @param map 查询条件，实体类的各属性组成的Map对象
     * @return 查询到的记录对应的实体对象集合
     * @exception DataAccessException 数据库访问异常
     */
    @SuppressWarnings({"unchecked", "rawtypes", "hiding"})
    public <T> List<T> getByPojo(Map map) throws DataAccessException {
        if (map == null)
            return this.getSqlMapClientTemplate().queryForList(
                    entityClass.getName() + POSTFIX_PAGE_QUERY, null);
        else {
            return this.getSqlMapClientTemplate().queryForList(
                    entityClass.getName() + POSTFIX_PAGE_QUERY, map);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List pageQuery(Class entityClass, int startIndex, int pageSize, Map param) throws DataAccessException {
        long beginNum = 0L;
        long endNum = 0L;
        //下面的计算适合oracle数据库分页
        //endNum = pageSize + startIndex;
        //下面的计算适合mysql数据库分页
        endNum = pageSize;
        beginNum = startIndex;

        if(param == null)
            param = new HashMap<String, String>();
        else{
            // 拼装排序的SQL
            StringBuilder orderByCondition = new StringBuilder();
            String orderbyCols = (String)param.get(ORDER_BY_COLUMNS); // 排序字段
            String sortTypes = (String)param.get(SORT_TYPES); // 排序方式
            if(orderbyCols != null && sortTypes != null){
                String[] arrCols = orderbyCols.split(",");
                String[] arrTypes = sortTypes.split(",");
                Assert.isTrue(arrCols.length == arrTypes.length, "The length of '"+ORDER_BY_COLUMNS+"' is not equals the length of '"+SORT_TYPES+"'");
                for(int i =0; i < arrCols.length; i++){
                    if(arrCols[i].trim().length() == 0) continue;
                    if(i < arrCols.length - 1) {
                        orderByCondition.append(arrCols[i] + " " + (arrTypes[i].trim().length()==0 ? SORT_ASC : arrTypes[i]) + ", ");
                    }else{
                        orderByCondition.append(arrCols[i] + " " + (arrTypes[i].trim().length()==0 ? SORT_ASC : arrTypes[i]));
                    }
                }
            }else if(orderbyCols != null){
                String[] arrCols = orderbyCols.split(",");
                for(int i =0; i < arrCols.length; i++){
                    if(arrCols[i].trim().length() == 0) continue;
                    if(i < arrCols.length - 1) {
                        orderByCondition.append(arrCols[i] + " " + SORT_ASC + ", ");
                    }else{
                        orderByCondition.append(arrCols[i] + " " + SORT_ASC);
                    }
                }
            }
            param.put(SQLMAP_ORDERBY, orderByCondition.toString());
        }
        param.put(SQLMAP_PAGE_LIMIT_START, new Long(beginNum));
        param.put(SQLMAP_PAGE_LIMIT_SIZE, new Long(endNum));

        return this.getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_PAGE_QUERY, param);
    }

    /**
     * 支持获取总记录数的分页查询。可设置查询条件、排序的字段、排序方式（升序、降序）<br/>
     *
     * @param parameterObject 查询信息的Map对象，包括：查询条件、排序的字段、排序方式（升序、降序）。
     *                        <p>
     *                            排序方式（升序、降序），可通过设置Map的key为{@link #SORT_TYPES}，value取值范围：
     *                            <ul>
     *                              <li>{@link #SORT_ASC 升序}</li>
     *                              <li>{@link #SORT_DESC 降序}</li>
     *                            </ul>
     *                        </p>
     *                        <p>
     *                            排序的字段，可通过设置Map的key为{@link #ORDER_BY_COLUMNS}，value为具体的字段名
     *                        </p>
     * @param pageNo 页号。大于零的整数
     * @param pageSize 每页显示记录数。大于零的整数
     *
     * @return 含当前页数据的Page对象。
     *
     * @exception DataAccessException 数据库访问异常
     * @exception IllegalArgumentException pageNo和pageSize参数异常
     */
    public Page<T> pageQueryWithTotal(Map parameterObject, int pageNo, int pageSize) throws DataAccessException, IllegalArgumentException {

        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        Assert.isTrue(pageSize > 0, "pageSize should be great than zero");

        // 计算总数
        Integer totalCount = (Integer) getTotalRows(entityClass, parameterObject);
        logger.debug("totalCount="+totalCount);
        // 如果没有数据则返回Empty Page
        Assert.notNull(totalCount, "totalCount Error");

        if (totalCount.intValue() == 0) {
            return new Page<T>(pageNo, pageSize, totalCount);
        }

        // 计算页数
        int totalPageCount = (totalCount / pageSize);
        totalPageCount += ((totalCount % pageSize) > 0) ? 1 : 0;
        logger.debug("totalPageCount="+totalPageCount);

        // 计算skip数量
        int startIndex = 0;
        if (totalPageCount > pageNo) {
            startIndex = (pageNo - 1) * pageSize;
        } else {
            startIndex = (totalPageCount - 1) * pageSize;
        }

        List<T> list = pageQuery(entityClass, startIndex, pageSize, parameterObject);

        return new Page(pageNo, pageSize, totalCount, list);
    }

    /**
     * 分页查询。可设置查询条件、排序的字段、排序方式（升序、降序）<br/>
     *
     * @param parameterObject 查询信息的Map对象，可包括：查询条件、排序的字段、排序方式（升序、降序）。
     *                        <p>
     *                            排序的字段，可通过设置Map的key为{@link #ORDER_BY_COLUMNS}，value为具体的字段名，多个
     *                            字段之间以逗号分隔。比如：<br/>
     *                            ${@code map.put(BaseDao.ORDER_BY_COLUMNS, "COLUMN1,COUMN2,...");}
     *                        </p>
     *                        <p>
     *                            排序方式（升序、降序），可通过设置Map的key为{@link #SORT_TYPES}，value为排序字段的
     *                            排序方式，value取值范围：
     *                            <ul>
     *                              <li>{@link #SORT_ASC 升序}</li>
     *                              <li>{@link #SORT_DESC 降序}</li>
     *                            </ul>
     *                            示例：<br/>
     *                            ${@code map.put(BaseDao.SORT_TYPES, "asc,desc,...");}
     *                        </p>
     *
     * @param pageNo 页号。大于零的整数
     * @param pageSize 每页显示记录数。大于零的整数
     *
     * @return {@code Page}分页器对象。
     * @see com.cssweb.payment.common.util.Page
     * @exception DataAccessException 数据库访问异常
     * @exception IllegalArgumentException pageNo和pageSize参数异常
     */
    public Page<T> pageQuery(Map parameterObject, int pageNo, int pageSize) throws DataAccessException, IllegalArgumentException {

        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        Assert.isTrue(pageSize > 0, "pageSize should be great than zero");

        int startIndex = (pageNo - 1) * pageSize;
        int nextPageSize = pageSize+1;
        List<T> list = pageQuery(entityClass, startIndex, nextPageSize, parameterObject);
        boolean hasNextPage = false;
        if(list != null && list.size()==nextPageSize){
            hasNextPage = true;
        }
        list.remove(list.size()-1);
        Page page = new Page(pageNo, pageSize, list);
        page.setHasNextPage(hasNextPage);

        return page;
    }

    @SuppressWarnings("rawtypes")
    private int getTotalRows(Class entityClass, Map param) throws DataAccessException {
        return (Integer) this.getSqlMapClientTemplate().queryForObject(
                entityClass.getName() + POSTFIX_PAGE_COUNT, param);
    }

    /**
     * 按条件查询到的记录数
     *
     * @param map 查询条件
     * @return 记录总数
     * @exception DataAccessException 数据库访问异常
     */
    @SuppressWarnings("rawtypes")
    public int getTotalRows(Map map) throws DataAccessException {
        return getTotalRows(getEntityClass(), map);
    }

    /**
     * 获取主键的变量名，默认与POJO类的ID同名
     *
     * @param clazz
     * @return 主键变量名
     */
    @SuppressWarnings("rawtypes")
    protected String getPrimaryKeyName(Class clazz) {
        if (primaryKeyName == null || primaryKeyName.trim().length() == 0)
            primaryKeyName = getIdName(clazz);
        return primaryKeyName;
    }

    /**
     * 获取实体类的主键对应的属性名。
     * 默认是实体类的第一个以ID结尾的属性名，在取不到的情况下，默认用{@link #DEFAULT_ID_NAME}代替
     *
     * @return 实体类的主键对应的属性名
     */
    private String getIdName(Class clazz) {
        String idName = DEFAULT_ID_NAME;

        //Returns an array of Field objects reflecting all the fields declared by the class or interface represented by this Class object.
        Field[] fields = clazz.getDeclaredFields();

        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                String tmpPrimaryKeyName = fields[i].getName();
                if (!tmpPrimaryKeyName.toLowerCase().equalsIgnoreCase("serialVersionUID") && tmpPrimaryKeyName.toLowerCase().endsWith("id")) {
                    idName = tmpPrimaryKeyName;
                    break;
                }
            }
        }
        return idName;
    }

    /**
     * 获取POJO类包名，用于sqlmap
     *
     * @param clazz
     * @param i
     * @return 类型
     */
    protected Class<?> getGenerictClass(Class<?> clazz, int i) {
        logger.debug("clazz=" + clazz);
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            logger.debug("getRawType=" + (Class<?>) ((ParameterizedType) genericClass).getRawType());
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            logger.debug("getGenericComponentType=" + (Class<?>) ((GenericArrayType) genericClass) .getGenericComponentType());
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
        } else {
            logger.debug("genericClass=" + (Class<?>) genericClass);
            return (Class<?>) genericClass;
        }
    }

    /**
     * 获取所管理的实体类的类型
     * @return 实体类的类型
     */
    protected Class<?> getEntityClass() {
        logger.debug("getGenerictClass=" + getGenerictClass(this.getClass(), 0));
        return getGenerictClass(this.getClass(), 0);
    }

    /**
     * slf4j 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        Map param = new HashMap();
        param.put(ORDER_BY_COLUMNS, "account,name,age");
        param.put(SORT_TYPES, "desc,,,,");
        StringBuilder orderByCondition = new StringBuilder();
        String orderbyCols = (String)param.get(ORDER_BY_COLUMNS); // 排序字段
        String sortTypes = (String)param.get(SORT_TYPES); // 排序方式
        if(orderbyCols != null && sortTypes != null){
            String[] arrCols = orderbyCols.split(",");
            String[] arrTypes = sortTypes.split(",");
            Assert.isTrue(arrCols.length == arrTypes.length, "The length of '"+ORDER_BY_COLUMNS+"' is not equals the length of '"+SORT_TYPES+"'");
            for(int i =0; i < arrCols.length; i++){
                if(arrCols[i].trim().length() == 0) continue;
                if(i < arrCols.length - 1) {
                    orderByCondition.append(arrCols[i] + " " + (arrTypes[i].trim().length()==0 ? SORT_ASC : arrTypes[i]) + ", ");
                }else{
                    orderByCondition.append(arrCols[i] + " " + (arrTypes[i].trim().length()==0 ? SORT_ASC : arrTypes[i]));
                }
            }
        }else if(orderbyCols != null){
            String[] arrCols = orderbyCols.split(",");
            for(int i =0; i < arrCols.length; i++){
                if(arrCols[i].trim().length() == 0) continue;
                if(i < arrCols.length - 1) {
                    orderByCondition.append(arrCols[i] + " " + SORT_ASC + ", ");
                }else{
                    orderByCondition.append(arrCols[i] + " " + SORT_ASC);
                }
            }
        }
        System.out.println(orderByCondition);
    }
}
