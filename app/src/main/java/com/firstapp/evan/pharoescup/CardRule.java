package com.firstapp.evan.pharoescup;

/**
 * Created by evan on 28/10/15.
 */
public class CardRule {
    public String rule;
    public String description;

    public CardRule(String rule, String description) {
        this.rule = rule;
        this.description = description;
    }

    public String getRule() {
        return rule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
