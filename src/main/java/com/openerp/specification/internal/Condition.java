package com.openerp.specification.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Condition {

    public Type type;
    public Comparison comparison;
    public Object value;
    public String field;

    public static class Builder {
        private Type type;
        private Comparison comparison;
        private Object value;
        private String field;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setComparison(Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        public Condition build() {
            return new Condition(type, comparison, value, field);
        }
    }
}