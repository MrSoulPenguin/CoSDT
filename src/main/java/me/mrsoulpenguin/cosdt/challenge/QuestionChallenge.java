package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class QuestionChallenge extends AbstractChallenge {

    private final String question;

    public QuestionChallenge(PlayerEntity participant, String question, Goal goal, Event rewardEvent, Event punishmentEvent) {
        super(participant, goal, rewardEvent, punishmentEvent);

        this.question = question;
    }

    @Override
    public void start() {
        this.getParticipant().sendMessage(Text.of(this.question));
    }
}
