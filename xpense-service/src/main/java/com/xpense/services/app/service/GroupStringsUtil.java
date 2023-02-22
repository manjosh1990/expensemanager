package com.xpense.services.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupStringsUtil {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        input.add("ACH D- INDIAN CLEARING CORP-P7797904X024");
        input.add("UPI-CREDCLUB1-CRED.CLUB@AXISB-UTIB0000114-224846546548-PAYMENT ON CRED");
        input.add("UPI-CREDCLUB1-CRED.CLUB@AXISB-UTIB0000114-224878985458-PAYMENT ON CRED");
        input.add("ACH D- INDIAN CLEARING CORP-P7797904X024");
        input.add("ACH D- INDIAN CLEARING CORP-P7797904X026");
        input.add("ACH D- INDIAN CLEARING CORP-P7797904X029");
        input.add("ACH D- INDIAN CLEARING CORP-D7797904X024");
        input.add("ACH C- SAL-MAVENIR SYSTEMS-SALARYSEP1000");

        Map<String, List<String>> groups = StringGrouper.groupSimilarStrings(input, 10);
        System.out.println(groups);
    }
    public static Map<String, List<String>> groupString(List<String> stringList){
        return StringGrouper.groupSimilarStrings(stringList,25);
    }

    public static List<String> getPrefixFromGroups(Map<String,List<String>> groups){
        return StringGrouper.getPrefixList(groups);
    }
}
class StringGrouper {
    private static int editDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
    //levenshtein distance calculation
    public static Map<String, List<String>> groupSimilarStrings(List<String> strings, int maxDistance) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String str1 : strings) {
            boolean added = false;
            for (String str2 : groups.keySet()) {
                if (editDistance(str1, str2) <= maxDistance) {
                    groups.get(str2).add(str1);
                    added = true;
                    break;
                }
            }
            if (!added) {
                List<String> newGroup = new ArrayList<>();
                newGroup.add(str1);
                groups.put(str1, newGroup);
            }
        }
        return groups;
    }
    public static List<String> getPrefixList(Map<String,List<String>> groups){
        List<String> prefixes = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : groups.entrySet()){
            List<String> group = entry.getValue();
            if(group.size() == 1){
                prefixes.add(group.get(0).substring(0,Math.min(group.get(0).length(),30))); //get first 10 characters;
                continue;
            }
            String prefix = longestCommonPrefix(group);
            if(!prefix.isEmpty()){
                prefixes.add(prefix);
            }
        }
        return prefixes;
    }
    public static String longestCommonPrefix(List<String> inputStrings){
        String prefix="";
        if(inputStrings ==null || inputStrings.isEmpty()) return prefix;

        prefix = inputStrings.get(0);
        for(int i =1; i<inputStrings.size();i++){
            while (inputStrings.get(i).indexOf(prefix) != 0){
                prefix = prefix.substring(0,prefix.length()-1);
            }
        }
        return prefix;
    }
}
