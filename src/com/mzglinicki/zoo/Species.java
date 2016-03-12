package com.mzglinicki.zoo;

public enum Species {
    TIGER(Constants.TIGRES) {
        @Override
        public Animal getAnimal() {
            return new Tiger();
        }
    },
    SNAKE(Constants.SNAKES) {
        @Override
        public Animal getAnimal() {
            return new Snake();
        }
    },
    GIRAFFE(Constants.GIRAFFE) {
        @Override
        public Animal getAnimal() {
            return new Giraffe();
        }
    },
    PTERODACTYL(Constants.PTERODACTYL) {
        @Override
        public Animal getAnimal() {
            return new Pterodactyl();
        }
    }, SUGAR_GLIDER(Constants.SUGAR_GLIDER) {
        @Override
        public Animal getAnimal() {
            return new SugarGlider();
        }
    };

    private String type;

    private Species(final String type) {
        this.type = type;
    }

    public abstract Animal getAnimal();

    public String getType() {
        return type;
    }
}
