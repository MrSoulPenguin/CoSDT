package me.mrsoulpenguin.cosdt.challenge.goal;

public class AnswerQuestionGoal extends AbstractGoal {

    private final String answer;

    public AnswerQuestionGoal(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean test(Object object) {
        if (object instanceof String string) {
            return string.equals(this.answer);
        }

        return false;
    }
}
