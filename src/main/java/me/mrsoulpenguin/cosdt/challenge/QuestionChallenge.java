package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.AnswerQuestionGoal;
import me.mrsoulpenguin.cosdt.challenge.event.Event;
import net.minecraft.text.Text;

public class QuestionChallenge extends AbstractChallenge {

    private final String question;

    public QuestionChallenge(AnswerQuestionGoal goal, Event rewardEvent, Event punishmentEvent) {
        super(goal, rewardEvent, punishmentEvent);

        this.question = goal.getQuestion();
    }

    @Override
    public void start() {
        this.getParticipant().sendMessage(Text.of(this.question));
    }
}
