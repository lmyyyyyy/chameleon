package cn.code.chameleon.monitor.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapperLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MapperLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdIsNull() {
            addCriterion("serivce_log_id is null");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdIsNotNull() {
            addCriterion("serivce_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdEqualTo(Long value) {
            addCriterion("serivce_log_id =", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdNotEqualTo(Long value) {
            addCriterion("serivce_log_id <>", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdGreaterThan(Long value) {
            addCriterion("serivce_log_id >", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("serivce_log_id >=", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdLessThan(Long value) {
            addCriterion("serivce_log_id <", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdLessThanOrEqualTo(Long value) {
            addCriterion("serivce_log_id <=", value, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdIn(List<Long> values) {
            addCriterion("serivce_log_id in", values, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdNotIn(List<Long> values) {
            addCriterion("serivce_log_id not in", values, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdBetween(Long value1, Long value2) {
            addCriterion("serivce_log_id between", value1, value2, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andSerivceLogIdNotBetween(Long value1, Long value2) {
            addCriterion("serivce_log_id not between", value1, value2, "serivceLogId");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameIsNull() {
            addCriterion("target_table_name is null");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameIsNotNull() {
            addCriterion("target_table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameEqualTo(String value) {
            addCriterion("target_table_name =", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameNotEqualTo(String value) {
            addCriterion("target_table_name <>", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameGreaterThan(String value) {
            addCriterion("target_table_name >", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("target_table_name >=", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameLessThan(String value) {
            addCriterion("target_table_name <", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameLessThanOrEqualTo(String value) {
            addCriterion("target_table_name <=", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameLike(String value) {
            addCriterion("target_table_name like", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameNotLike(String value) {
            addCriterion("target_table_name not like", value, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameIn(List<String> values) {
            addCriterion("target_table_name in", values, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameNotIn(List<String> values) {
            addCriterion("target_table_name not in", values, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameBetween(String value1, String value2) {
            addCriterion("target_table_name between", value1, value2, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andTargetTableNameNotBetween(String value1, String value2) {
            addCriterion("target_table_name not between", value1, value2, "targetTableName");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNull() {
            addCriterion("operate_type is null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNotNull() {
            addCriterion("operate_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeEqualTo(Byte value) {
            addCriterion("operate_type =", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotEqualTo(Byte value) {
            addCriterion("operate_type <>", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThan(Byte value) {
            addCriterion("operate_type >", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("operate_type >=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThan(Byte value) {
            addCriterion("operate_type <", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("operate_type <=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIn(List<Byte> values) {
            addCriterion("operate_type in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotIn(List<Byte> values) {
            addCriterion("operate_type not in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeBetween(Byte value1, Byte value2) {
            addCriterion("operate_type between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("operate_type not between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowIsNull() {
            addCriterion("influence_row is null");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowIsNotNull() {
            addCriterion("influence_row is not null");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowEqualTo(Integer value) {
            addCriterion("influence_row =", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowNotEqualTo(Integer value) {
            addCriterion("influence_row <>", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowGreaterThan(Integer value) {
            addCriterion("influence_row >", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowGreaterThanOrEqualTo(Integer value) {
            addCriterion("influence_row >=", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowLessThan(Integer value) {
            addCriterion("influence_row <", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowLessThanOrEqualTo(Integer value) {
            addCriterion("influence_row <=", value, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowIn(List<Integer> values) {
            addCriterion("influence_row in", values, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowNotIn(List<Integer> values) {
            addCriterion("influence_row not in", values, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowBetween(Integer value1, Integer value2) {
            addCriterion("influence_row between", value1, value2, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInfluenceRowNotBetween(Integer value1, Integer value2) {
            addCriterion("influence_row not between", value1, value2, "influenceRow");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusIsNull() {
            addCriterion("invoke_status is null");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusIsNotNull() {
            addCriterion("invoke_status is not null");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusEqualTo(Byte value) {
            addCriterion("invoke_status =", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusNotEqualTo(Byte value) {
            addCriterion("invoke_status <>", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusGreaterThan(Byte value) {
            addCriterion("invoke_status >", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("invoke_status >=", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusLessThan(Byte value) {
            addCriterion("invoke_status <", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusLessThanOrEqualTo(Byte value) {
            addCriterion("invoke_status <=", value, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusIn(List<Byte> values) {
            addCriterion("invoke_status in", values, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusNotIn(List<Byte> values) {
            addCriterion("invoke_status not in", values, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusBetween(Byte value1, Byte value2) {
            addCriterion("invoke_status between", value1, value2, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andInvokeStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("invoke_status not between", value1, value2, "invokeStatus");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTimeCostIsNull() {
            addCriterion("time_cost is null");
            return (Criteria) this;
        }

        public Criteria andTimeCostIsNotNull() {
            addCriterion("time_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTimeCostEqualTo(Long value) {
            addCriterion("time_cost =", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostNotEqualTo(Long value) {
            addCriterion("time_cost <>", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostGreaterThan(Long value) {
            addCriterion("time_cost >", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostGreaterThanOrEqualTo(Long value) {
            addCriterion("time_cost >=", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostLessThan(Long value) {
            addCriterion("time_cost <", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostLessThanOrEqualTo(Long value) {
            addCriterion("time_cost <=", value, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostIn(List<Long> values) {
            addCriterion("time_cost in", values, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostNotIn(List<Long> values) {
            addCriterion("time_cost not in", values, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostBetween(Long value1, Long value2) {
            addCriterion("time_cost between", value1, value2, "timeCost");
            return (Criteria) this;
        }

        public Criteria andTimeCostNotBetween(Long value1, Long value2) {
            addCriterion("time_cost not between", value1, value2, "timeCost");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}