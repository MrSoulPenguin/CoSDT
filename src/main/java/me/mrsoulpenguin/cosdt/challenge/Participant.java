package me.mrsoulpenguin.cosdt.challenge;

import me.mrsoulpenguin.cosdt.challenge.event.TickingEvent;

import java.util.Set;

public interface Participant {

    void addChallenge(Challenge challenge);

    void addTickingEvent(TickingEvent event);

    void removeChallenge(Challenge challenge);

    void removeTickingEvent(TickingEvent event);

    Set<Challenge> getActiveChallenges();

    Set<TickingEvent> getActiveTickingEvents();

}
