package com.egencia.test;

import java.util.Arrays;

public enum Skill {
    UNKNOWN(0),
    EMPATHY(1),
    ARCHERY(2),
    SWORDSMANSHIP(3),
    WAR_STRATEGY(4),
    FIRST_AID(5),
    LOYALTY(6);


    int index;

    Skill(int skillIndex) {
        this.index = skillIndex;
    }

    public int getIndex() {
        return index;
    }

    public static Skill fromInt(int value) {
        return Arrays.asList(Skill.values())
                .stream()
                .filter(skill -> skill.getIndex() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
