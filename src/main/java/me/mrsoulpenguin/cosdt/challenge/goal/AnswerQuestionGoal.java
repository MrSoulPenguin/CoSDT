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
            return response.equals(this.answer);
        }

        return false;
    }
}
