package com.n7ws.back;

public class Format {
    public static String centeredHeaderTitle(String title, Integer width, Character fillChar) {
        String wrappedTitle = "[" + title + "]";
        Integer totalPadding = width - wrappedTitle.length();
        Integer rightPadding = totalPadding / 2;
        Integer leftPadding = totalPadding - rightPadding;

        return String.format("%s%s%s",
            String.valueOf(fillChar).repeat(leftPadding),
            wrappedTitle,
            String.valueOf(fillChar).repeat(rightPadding)
        );
    }

    public static String centeredFooter(Integer width, Character fillChar) {
        return String.valueOf(fillChar).repeat(width);
    }
}
