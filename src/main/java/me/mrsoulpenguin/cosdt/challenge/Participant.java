package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.event.Event;

import java.util.Set;

public interface Participant {

    void addChallenge(Challenge challenge);

    void addPunishment(Event punishment);

    void removeChallenge(Challenge challenge);

    void removePunishment(Event punishment);

    Set<Challenge> getActiveChallenges();

    Set<Event> getActivePunishments();

}
