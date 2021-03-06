package com.despegar.integration.mongo.query;

import java.util.Collection;

public abstract class Expression {

    static enum ExpressionOperator {
        CONDITIONAL,
        MULTIPLY,
        ADD,
        DIVIDE,
        SUBTRACT,
        GREAT_THAN_EQUAL,
        GREAT_THAN,
        LESS_THAN,
        LESS_THAN_EQUAL,
        EQUAL,
        NON_EQUAL,
        SIZE,
        AVG,
        FIRST,
        LAST,
        SUM,
        PUSH,
        ADD_TO_SET,
        MAX,
        MIN,
        HOUR,
        MINUTES,
        YEAR,
        MONTH,
        DAY,
        SET_INTERSECTION
    };

    protected ExpressionOperator operator;
    protected Object[] parameters;

    public static Arithmetical multiply(Object... parameters) {
        return new Arithmetical(ExpressionOperator.MULTIPLY, parameters);
    }

    public static Arithmetical add(Object... parameters) {
        return new Arithmetical(ExpressionOperator.ADD, parameters);
    }

    public static Arithmetical subtract(Object... parameters) {
        return new Arithmetical(ExpressionOperator.SUBTRACT, parameters);
    }

    public static Arithmetical divide(Object dividend, Object divisor) {
        return new Arithmetical(ExpressionOperator.DIVIDE, dividend, divisor);
    }

    public static Conditional cond(Comparison comparison, Object then, Object els) {
        return new Conditional(ExpressionOperator.CONDITIONAL, comparison, then, els);
    }

    public static Conditional cond(String property, Object then, Object els) {
        return new Conditional(ExpressionOperator.CONDITIONAL, property, then, els);
    }

    public static Comparison greatThanEqual(Object value1, Object value2) {
        return new Comparison(ExpressionOperator.GREAT_THAN_EQUAL, value1, value2);
    }

    public static Comparison greatThan(Object value1, Object value2) {
        return new Comparison(ExpressionOperator.GREAT_THAN, value1, value2);
    }

    public static Comparison lessThanEqual(Object value1, Object value2) {
        return new Comparison(ExpressionOperator.LESS_THAN_EQUAL, value1, value2);
    }

    public static Comparison equal(Object value1, Object value2) {
        return new Comparison(ExpressionOperator.EQUAL, value1, value2);
    }

    public static Comparison nonEqual(Object value1, Object value2) {
        return new Comparison(ExpressionOperator.NON_EQUAL, value1, value2);
    }

    public static Array size(Object expression) {
        return new Array(ExpressionOperator.SIZE, expression);
    }

    public static Accumulators first(Object expression) {
        return new Accumulators(ExpressionOperator.FIRST, expression);
    }

    public static Accumulators last(Object expression) {
        return new Accumulators(ExpressionOperator.LAST, expression);
    }

    public static Accumulators avg(Object expression) {
        return new Accumulators(ExpressionOperator.AVG, expression);
    }

    public static Accumulators min(Object expression) {
        return new Accumulators(ExpressionOperator.MIN, expression);
    }

    public static Accumulators max(Object expression) {
        return new Accumulators(ExpressionOperator.MAX, expression);
    }

    public static Accumulators sum(Object expression) {
        return new Accumulators(ExpressionOperator.SUM, expression);
    }

    public static Accumulators push(Object expression) {
        return new Accumulators(ExpressionOperator.PUSH, expression);
    }

    public static Accumulators addToSet(Object expression) {
        return new Accumulators(ExpressionOperator.ADD_TO_SET, expression);
    }

    public static Date hour(Object expression) {
        return new Date(ExpressionOperator.HOUR, expression);
    }

    public static Date minutes(Object expression) {
        return new Date(ExpressionOperator.MINUTES, expression);
    }

    public static Date year(Object expression) {
        return new Date(ExpressionOperator.YEAR, expression);
    }

    public static Date month(Object expression) {
        return new Date(ExpressionOperator.MONTH, expression);
    }

    public static Date day(Object expression) {
        return new Date(ExpressionOperator.DAY, expression);
    }

    public static Set setIntersection(String property, Expression expression) {
        return new Set(ExpressionOperator.SET_INTERSECTION, property, expression);
    }

    public static Set setIntersection(Collection<?> list, Expression expression) {
        return new Set(ExpressionOperator.SET_INTERSECTION, list, expression);
    }

    public static Set setIntersection(String property, Collection<?> list) {
        return new Set(ExpressionOperator.SET_INTERSECTION, list, property);
    }

    static class Arithmetical
        extends Expression {

        public Arithmetical(ExpressionOperator op, Object... operators) {
            this.operator = op;
            this.parameters = operators;
        }
    }

    static class Comparison
        extends Expression {

        public Comparison(ExpressionOperator op, Object value1, Object value2) {
            this.operator = op;
            this.parameters = new Object[] {value1, value2};
        }
    }

    static class Conditional
        extends Expression {

        public Conditional(ExpressionOperator op, Object i, Object then, Object els) {
            this.operator = op;
            this.parameters = new Object[] {i, then, els};
        }
    }

    static class Array
        extends Expression {

        public Array(ExpressionOperator op, Object expression) {
            this.operator = op;
            this.parameters = new Object[] {expression};
        }
    }

    static class Accumulators
        extends Expression {

        public Accumulators(ExpressionOperator op, Object expression) {
            this.operator = op;
            this.parameters = new Object[] {expression};
        }
    }

    static class Date
        extends Expression {

        public Date(ExpressionOperator op, Object expression) {
            this.operator = op;
            this.parameters = new Object[] {expression};
        }
    }

    static class Set
        extends Expression {

        public Set(ExpressionOperator op, Object list1, Object list2) {
            this.operator = op;
            this.parameters = new Object[] {list1, list2};
        }
    }

    public ExpressionOperator getOperator() {
        return this.operator;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

}
