package uz.kuvondikov.clickup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum Permission {
    PERMISSION_ADD_REMOVE("Add/Remove Members", "Remove and added members"),
    PERMISSION_EDIT("Edit Members", "Edite members");

    public final String title;
    public final String description;
}
