package edu.rosehulman.brusniss.mobilementor.profile;

import androidx.annotation.IntDef;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Retention(AnnotationRetention.SOURCE)
@IntDef({PermissionLevel.REGULAR, PermissionLevel.MENTOR, PermissionLevel.PROFESSOR, PermissionLevel.ADMIN})
public @interface PermissionLevel {
    int REGULAR = 0;
    int MENTOR = 1;
    int PROFESSOR = 2;
    int ADMIN = 3;
}
