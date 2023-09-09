package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.AnswerQuestionGoal;
import me.mrsoulpenguin.cosdt.challenge.event.Event;

public class QuestionChallenge extends Challenge {

    public QuestionChallenge(AnswerQuestionGoal goal, Event rewardEvent, Event punishmentEvent) {
        super(goal, rewardEvent, punishmentEvent);
    }

}
