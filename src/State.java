public enum State {
    ACTIVE("#"),
    INACTIVE(".");

    private String character;

    State(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
