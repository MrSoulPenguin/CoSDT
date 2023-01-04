package me.mrsoulpenguin.cosdt.challenge;

import java.util.Set;

public interface ChallengeHolder {

    void addChallenge(AbstractChallenge challenge);

    void removeChallenge(AbstractChallenge challenge);

    Set<AbstractChallenge> getActiveChallenges();

}
