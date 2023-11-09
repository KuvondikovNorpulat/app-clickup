package uz.kuvondikov.clickup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {

    COLOR_RED("red", "#FF0000"),
    COLOR_PINK("pink", "#FFC0CB"),
    COLOR_ORANGE("orange", "#FFA500"),
    COLOR_PURPLE("purple", "#800080"),
    COLOR_GREEN("green", "#008000"),
    COLOR_YELLOW("yellow", "#FFFF00"),
    COLOR_WHITE("white", "#FFFFFF"),
    COLOR_BLACK("black", "#000000"),
    COLOR_AQUA("aqua", "#00FFFF"),
    COLOR_BLUE("blue", "#0000FF");

    private final String name;
    private final String hexCode;

}
