enum Status {
    Normal,
    Defense,
    Buffed
}

enum Pages {
    Main(0),
    Fight(1);

    private final int value;
    private Pages(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum Sounds {
    BGEntry("audio/entrance.wav"),
    BGFight("audio/battletheme.wav"),
    BGWin("audio/win.wav"),
    BGLoss("audio/loss.wav"),
    SFXHit("audio/damage.wav"),
    SFXPowerup("audio/powerup.wav");

    private final String value;
    private Sounds(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}