package com.despegar.integration.mongo.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.util.Assert;

public class HandlerQuery {

    public static enum OrderDirection {
        ASC, DESC
    }

    public static enum RangeOperation {
        IN, NOT_IN, ALL
    }

    public static enum ComparisonOperation {
        GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, NOT_EQUAL
    }

    public static enum UpdateOperation {
        SET, UNSET, INC, RENAME, ADD_TO_SET, POP, PULL_ALL, PULL, PUSH
    }

    private Map<String, OperationWithComparison> comparisonOperators = new HashMap<String, OperationWithComparison>();
    private Map<String, OperationWithRange> rangeOperators = new HashMap<String, OperationWithRange>();
    private Map<String, Object> filters = new HashMap<String, Object>();
    private OrderedMap orderFields = new ListOrderedMap();
    private UpdateOperation updateOperation = null;

    private List<HandlerQuery> ors = new ArrayList<HandlerQuery>();

    private Page page;

    public HandlerQuery() {
    }

    public HandlerQuery put(String key, Object value) {

        if (key != null) {
            this.getFilters().put(key, value);
        }

        return this;
    }

    /**
     * Search the key value matching the operation described over the values present in the collection
     * @param key
     * @param values
     * @return
     */
    public HandlerQuery put(String key, RangeOperation operator, Collection<?> values) {
        if (values == null || values.size() == 0) {
            return this;
        } else if (values.size() == 1) {
            if (operator == RangeOperation.NOT_IN) {
                this.put(key, ComparisonOperation.NOT_EQUAL, values.iterator().next());
            } else {
                this.put(key, values.iterator().next());
            }

            return this;
        }
        this.getRangeOperators().put(key, new OperationWithRange(operator, values));
        return this;
    }


    /**
     * Search the key value matching the comparison operation with the value
     * @param key
     * @param values
     * @return
     */
    public HandlerQuery put(String key, ComparisonOperation operator, Object value) {
        OperationWithComparison operationWithComparison = this.getComparisonOperators().get(key);

        if (operationWithComparison != null) {
            operationWithComparison.addComparision(new OperationWithComparison(operator, value));
        } else {
            this.getComparisonOperators().put(key, new OperationWithComparison(operator, value));
        }
        return this;
    }

    /**
     * Add a field for sorting purpose, with a default direction (asc)
     */
    public HandlerQuery addOrderCriteria(String fieldName) {
        return this.addOrderCriteria(fieldName, OrderDirection.ASC);

    }

    @SuppressWarnings("unchecked")
    public HandlerQuery addOrderCriteria(String fieldName, OrderDirection direction) {
        Assert.notNull(fieldName, "Field name for sorting criteria is required.");
        this.orderFields.put(fieldName, direction);
        return this;
    }

    public Map<String, Object> getFilters() {
        return this.filters;
    }

    public OrderedMap getOrderFields() {
        return this.orderFields;
    }


    public Map<String, OperationWithRange> getRangeOperators() {
        if (this.rangeOperators == null) {
            this.rangeOperators = new HashMap<String, OperationWithRange>();
        }
        return this.rangeOperators;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return this.page;
    }

    public Map<String, OperationWithComparison> getComparisonOperators() {
        if (this.comparisonOperators == null) {
            this.comparisonOperators = new HashMap<String, HandlerQuery.OperationWithComparison>();
        }
        return this.comparisonOperators;
    }


    public HandlerQuery or(HandlerQuery anotherQuery) {
        this.ors.add(anotherQuery);
        return this;
    }


    public List<HandlerQuery> getOrs() {
        return this.ors;
    }


    public static class OperationWithComparison {
        private ComparisonOperation operation;
        private Object value;

        private List<OperationWithComparison> moreComparisions = new ArrayList<HandlerQuery.OperationWithComparison>();

        public OperationWithComparison(ComparisonOperation operation, Object value) {
            super();
            this.operation = operation;
            this.value = value;
        }

        public ComparisonOperation getOperation() {
            return this.operation;
        }

        public Object getValue() {
            return this.value;
        }

        public OperationWithComparison addComparision(OperationWithComparison another) {
            this.moreComparisions.add(another);
            return this;
        }

        public List<OperationWithComparison> getMoreComparisions() {
            return this.moreComparisions;
        }
    }

    public static class OperationWithRange {
        private RangeOperation rangeOperation;
        private Collection<?> values;

        public OperationWithRange(RangeOperation rangeOperation, Collection<?> values) {
            super();
            this.rangeOperation = rangeOperation;
            this.values = values;
        }

        public RangeOperation getCollectionOperation() {
            return this.rangeOperation;
        }

        public Collection<?> getValues() {
            return this.values;
        }

    }

    public UpdateOperation getUpdateOperation() {
        return this.updateOperation;
    }

    public void setUpdateOperation(UpdateOperation updateOperation) {
        this.updateOperation = updateOperation;
    }

}
