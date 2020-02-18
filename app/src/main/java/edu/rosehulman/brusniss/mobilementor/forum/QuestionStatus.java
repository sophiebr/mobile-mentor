package edu.rosehulman.brusniss.mobilementor.forum;

import androidx.annotation.IntDef;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Retention(AnnotationRetention.SOURCE)
@IntDef({QuestionStatus.UNANSWERED, QuestionStatus.MENTOR_ANSWERED, QuestionStatus.NOTIFICATION})
public @interface QuestionStatus {
    int UNANSWERED = 0;
    int MENTOR_ANSWERED = 1;
    int NOTIFICATION = 2;
}
