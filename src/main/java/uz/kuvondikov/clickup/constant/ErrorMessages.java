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
    public static final String WRONG_EMAIL_OR_VERIFICATION_CODE = "Email or verification code wrong";
    public static final String TELEGRAM_BOT_CONFIG_EXCEPTION = "Telegram bot configuration exception";
    public static final String WORKSPACE_ALREADY_EXISTS = " already has a workspace with this name:";
    public static final String USER_ALREADY_EXISTS = "Already has a user with this id:";
    public static final String OPERATION_CAN_NOT_PERFORMED ="This operation cannot be performed";

    public static final String NOT_STRENGTH_PASSWORD ="Password length must be between 8-16 and contain at least one uppercase letter, lowercase letter, symbol and number";
}
