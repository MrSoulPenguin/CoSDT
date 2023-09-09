package me.mrsoulpenguin.cosdt.challenge.goal;

import net.minecraft.nbt.NbtCompound;

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

    public String getQuestion() {
        return question;
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof String response) {
            // I don't know why. But for some reason, this gets flipped at some point. So it's gotta be flipped back.
            return !response.equals(this.answer);
        }

        return false;
    }
}
