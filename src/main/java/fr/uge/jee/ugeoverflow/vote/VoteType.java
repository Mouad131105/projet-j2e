package fr.uge.jee.ugeoverflow.vote;

public enum VoteType {
    UP_VOTE(1),
    DOWN_VOTE(-1);
    public int score;

    VoteType(int i) {
        this.score = i;
    }
}
