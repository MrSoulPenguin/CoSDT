package me.mrsoulpenguin.cosdt.challenge.goal;

import me.mrsoulpenguin.cosdt.Cosdt;
import me.mrsoulpenguin.cosdt.Registries;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

@SuppressWarnings("unused")
public class GoalType<T extends Goal> {

    public static final GoalType<AnswerQuestionGoal> ANSWER_QUESTION = register("answer_question", Builder.create(AnswerQuestionGoal::new));
    public static final GoalType<PickupItemGoal> PICKUP_ITEM = register("pickup_item", Builder.create(PickupItemGoal::new));

    private final GoalFactory<T> factory;

    public GoalType(GoalFactory<T> factory) {
        this.factory = factory;
    }

    private static <T extends Goal> GoalType<T> register(String id, Builder<T> type) {
        return Registry.register(Registries.GOAL_TYPE, new Identifier(Cosdt.NAMESPACE, id), type.build());
    }

    public T create(NbtCompound nbt) {
        return this.factory.create(nbt);
    }

    public static Optional<Goal> getGoalFromNbt(NbtCompound nbt) {
        return fromNbt(nbt).map(type -> type.create(nbt));

    }

    public static Optional<GoalType<?>> fromNbt(NbtCompound nbt) {
        return Registries.GOAL_TYPE.getOrEmpty(new Identifier(nbt.getString("id")));
    }

    private record Builder<T extends Goal>(GoalFactory<T> factory) {

        public static <T extends Goal> Builder<T> create(GoalFactory<T> factory) {
            return new Builder<>(factory);
        }

        public GoalType<T> build() {
            return new GoalType<>(this.factory);
        }

    }

    public interface GoalFactory<T extends Goal> {
        T create(NbtCompound nbt);
    }

}
