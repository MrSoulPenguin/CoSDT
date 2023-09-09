package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class AnswerQuestionGoal implements Goal {

    private final String question;
    private final String answer;

    public AnswerQuestionGoal(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public AnswerQuestionGoal(NbtCompound nbt) {
        this(nbt.getString("question"), nbt.getString("answer"));
    }

    @Override
    public void notifyParticipant(PlayerEntity participant) {
        participant.sendMessage(Text.of(this.question));
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof String response) {
            return response.equals(this.answer);
        }

        return false;
    }
}
