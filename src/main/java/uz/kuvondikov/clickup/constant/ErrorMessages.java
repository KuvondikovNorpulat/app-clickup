package uz.kuvondikov.clickup.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String USER_NOT_FOUND = "User not found : ";
    public static final String EXCEPTION_OCCURRED_WHILE_SAVING_FILE_WITH_NAME = "Exception Occurred while saving this file with name : ";
    public static final String UNCOMPRESSIBLE_FILE_LOADED_WITH_NAME = "UnCompressible file tried to compress; Compressor for only .jpg, .jpeg, .png; FileName : ";
    public static final String EXCEPTION_OCCURRED_WHILE_SAVING_FILE_WITH_ID = "Exception Occurred while downloading file with id :";
    public static final String ITEM_NOT_FOUND_WITH_THIS_ID = "Item not found with this id : ";
    public static final String AUTH_USER_EMAIL_ALREADY_EXISTS = "Email address already exist with name :";
}
