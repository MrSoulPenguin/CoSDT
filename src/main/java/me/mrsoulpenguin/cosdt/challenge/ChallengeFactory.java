package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.goal.AnswerQuestionGoal;
import me.mrsoulpenguin.cosdt.challenge.goal.Goal;
import me.mrsoulpenguin.cosdt.challenge.goal.GoalType;
import me.mrsoulpenguin.cosdt.challenge.event.Event;
import me.mrsoulpenguin.cosdt.challenge.event.EventType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.Optional;

public class ChallengeFactory {

    public static Challenge fromNbt(NbtCompound nbt) {
        Challenge challenge;

        Optional<Goal> goal = GoalType.getGoalFromNbt(nbt.getCompound("goal"));
        Optional<Event> punishment = EventType.getEventFromNbt(nbt.getCompound("punishment"));
        Optional<Event> reward = EventType.getEventFromNbt(nbt.getCompound("reward"));

        if (goal.isPresent() && punishment.isPresent() && reward.isPresent()) {
            if (nbt.contains("timed") && nbt.getBoolean("timed")) {
                challenge = new TimedChallenge(goal.get(), nbt.getLong("time_limit"), reward.get(), punishment.get());
            } else {
                challenge = new QuestionChallenge((AnswerQuestionGoal) goal.get(), reward.get(), punishment.get());
            }
        } else {
            challenge = new Challenge(null, null, null) {
                @Override
                public void start() {
                    PlayerEntity playerEntity = this.getParticipant();
                    playerEntity.sendMessage(Text.of("Something went wrong with a challenge, here's a cookie instead."));
                    playerEntity.getInventory().offer(Items.COOKIE.getDefaultStack(), true);
                    this.stop(ActionResult.PASS);
                }
            };
        }

        return challenge;
    }

}
