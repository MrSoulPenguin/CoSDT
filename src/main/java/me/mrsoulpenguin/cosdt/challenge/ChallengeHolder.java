package me.mrsoulpenguin.cosdt.challenge;

import java.util.Set;

public interface ChallengeHolder {

    void addChallenge(Challenge challenge);

    void removeChallenge(Challenge challenge);

    Set<Challenge> getActiveChallenges();

}
