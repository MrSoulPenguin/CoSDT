package me.mrsoulpenguin.cosdt.listener;

import me.mrsoulpenguin.cosdt.callback.PickupItemCallback;
import me.mrsoulpenguin.cosdt.challenge.AbstractChallenge;
import me.mrsoulpenguin.cosdt.challenge.ChallengeHolder;
import me.mrsoulpenguin.cosdt.challenge.goal.PickupItemGoal;
import net.minecraft.util.ActionResult;

import java.util.Iterator;

public class PickupItemListener {

    public PickupItemListener() {
        PickupItemCallback.EVENT.register((player, item) -> {
            /*
                Using iterator implementation to avoid a ConcurrentModificationException due to the challenge being
                removed from the players active challenges by stopping it, while looping through the challenges.
             */
            Iterator<AbstractChallenge> challengeIterator = ((ChallengeHolder) player).getActiveChallenges().stream()
                    .filter(challenge -> challenge.getGoal() instanceof PickupItemGoal)
                    .iterator();

            while (challengeIterator.hasNext()) {
                AbstractChallenge challenge = challengeIterator.next();
                if (challenge.getGoal().test(item)) {
                    challenge.stop(ActionResult.SUCCESS);
                }
            }

            return ActionResult.PASS;
        });
    }

}
