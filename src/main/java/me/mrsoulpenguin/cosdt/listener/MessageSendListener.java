package me.mrsoulpenguin.cosdt.listener;

import me.mrsoulpenguin.cosdt.callback.MessageSendCallback;
import me.mrsoulpenguin.cosdt.challenge.AbstractChallenge;
import me.mrsoulpenguin.cosdt.challenge.ChallengeHolder;
import me.mrsoulpenguin.cosdt.challenge.goal.AnswerQuestionGoal;
import net.minecraft.util.ActionResult;

import java.util.Iterator;

public class MessageSendListener {

    public MessageSendListener() {
        MessageSendCallback.EVENT.register((player, message) -> {
            Iterator<AbstractChallenge> challengeIterator = ((ChallengeHolder) player).getActiveChallenges().stream()
                    .filter(challenge -> challenge.getGoal() instanceof AnswerQuestionGoal)
                    .iterator();

            while (challengeIterator.hasNext()) {
                AbstractChallenge challenge = challengeIterator.next();
                if (challenge.getGoal().test(message)) {
                    challenge.stop(ActionResult.SUCCESS);
                }
            }

            return ActionResult.SUCCESS;
        });
    }

}
