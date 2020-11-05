package com.egencia.test;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class ValidationTest {

    public List<KnighthoodCheck> fromSoldierFileDataToModel(List<List<?>> inputFileData) {
        return inputFileData.stream()
                .map(input -> {
                    String name = (String) input.get(0);
                    Integer age = (Integer) input.get(1);
                    List<Skill> skills = (List<Skill>) input.get(2);
                    KnighthoodCheck knight = null;
                    /******************************
                     ***** Code Starts here. [Mapping]
                     ******************************/

                    /******************************
                     ***** Code Ends here. [Mapping]
                     ******************************/
                    return knight;
                })
                .collect(toList());
    }

    public List<KnighthoodCheck> fromSoldierCombatFileDataToModel(List<List<?>> inputFileData) {
        return inputFileData.stream()
                .map(input -> {
                    String name = (String) input.get(0);
                    Integer age = (Integer) input.get(1);
                    List<Skill> skills = (List<Skill>) input.get(2);
                    Integer combatLevel = (Integer) input.get(3);
                    KnighthoodCheck knight = null;
                    /******************************
                     ***** Code Starts here. [Mapping]
                     ******************************/

                    /******************************
                     ***** Code Ends here. [Mapping]
                     ******************************/
                    return knight;
                })
                .collect(toList());
    }

    /**
     * ### Exercise 1:  Find Sir Knight
     * <p>
     * Find all Eligible Knights
     * A person is considered Knight it:
     * - it's age is Between 16 and 55
     * - has one of the following Skills ARCHERY or SWORDSMANSHIP
     * - Is Loyal ( has Skill LOYALTY )
     */
    @ParameterizedTest
    @MethodSource("findTheKnightsInput")
    public void find_the_knight(String inputName, List<String> actualKnights) {
        List<List<?>> input = readInput(inputName, mapSoldiers());
        List<KnighthoodCheck> model = fromSoldierFileDataToModel(input);
        List<KnighthoodCheck> proposedKnights = null;
        /******************************
         ***** Code Starts here. [Exercise 1:  Find Sir Knight]
         ******************************/

        /******************************
         ***** Code Ends here. [Exercise 1:  Find Sir Knight]
         ******************************/

        List<String> proposedKnightsNames = proposedKnights.stream()
                .map(KnighthoodCheck::getName)
                .collect(toList());
        assertThat(proposedKnightsNames).containsExactlyElementsOf(actualKnights);
    }

    /**
     * ### Exercise 2: All hail the king
     * <p>
     * Find the one True King.
     * The king is the youngest knight.
     */
    @ParameterizedTest
    @MethodSource("findTheKingInput")
    public void find_the_king(String inputName, String king) {
        List<List<?>> input = readInput(inputName, mapSoldiers());
        List<KnighthoodCheck> model = fromSoldierFileDataToModel(input);
        KnighthoodCheck proposedKing = null;
        /******************************
         ***** Code Starts here. [Exercise 2: All hail the king]
         ******************************/

        /******************************
         ***** Code Ends here. [Exercise 2: All hail the king]
         ******************************/
        assertThat(proposedKing.getName()).isEqualTo(king);
    }

    /**
     * ### Exercise 3: Cruel King
     * <p>
     * All soldier except the king will be battling each other, and there will be one survivor only.
     * To survive one must have one combat Skill ( Either ARCHERY or SWORDSMANSHIP HIP) and one Assistant SKill (
     * WAR_STRATEGY or FIRST_AID)
     * Between all those eligible to survive the one with highest combat skill will victorious.
     */
    @ParameterizedTest
    @MethodSource("battleRoyalSurvivorInput")
    public void survive_the_battle_royal(String inputName, String actualWinner) {
        List<List<?>> input = readInput(inputName, mapCombatSoldiers());
        List<KnighthoodCheck> model = fromSoldierCombatFileDataToModel(input);
        KnighthoodCheck winner = null;
        /******************************
         ***** Code Starts here.
         ******************************/

        /******************************
         ***** Code Ends here.
         ******************************/
        assertThat(winner.getName()).isEqualTo(actualWinner);
    }

    //region ## Shouldn't be edited section
    public List<List<?>> readInput(String inputName, Function<String, List<?>> mapper) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(inputName);
        return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
                .lines()
                .map(mapper)
                .collect(toList());
    }


    private Function<String, List<?>> mapSoldiers() {
        return line -> {
            String[] parts = line.split(",");
            String name = parts[0];
            Integer age = parseInt(parts[1]);
            List<Skill> skills = buildSkills(parts.length > 2 ? parts[2] : "");
            return asList(name, age, skills);
        };
    }

    private Function<String, List<?>> mapCombatSoldiers() {
        return line -> {
            String[] parts = line.split(",");
            String name = parts[0];
            Integer age = parseInt(parts[1]);
            List<Skill> skills = buildSkills(parts.length > 2 ? parts[2] : "");
            Integer combatLevel = parseInt(parts[3]);
            return asList(name, age, skills, combatLevel);
        };
    }

    private List<Skill> buildSkills(String parts) {
        if (parts == null || parts.length() == 0) {
            return new ArrayList<>();
        } else {

            List<Skill> skills = new ArrayList<>();
            for (char c : parts.toCharArray()) {
                skills.add(Skill.fromInt(Integer.parseInt("" + c)));
            }
            return skills;
        }
    }

    public static Stream<Arguments> findTheKnightsInput() {
        return Stream.of(
                Arguments.of("soldiers.txt", asList("Neal", "Arthur", "Allie", "Elise"))
        );
    }

    public static Stream<Arguments> findTheKingInput() {
        return Stream.of(
                Arguments.of("soldiers.txt", "Arthur")
        );
    }

    public static Stream<Arguments> battleRoyalSurvivorInput() {
        return Stream.of(
                Arguments.of("soldiers-battle.txt", "Elise")
        );
    }
    //endregion
}
