package cn.orangepoet.inaction.gof.builder.effective;

/**
 * Target Type
 * 
 * @author orange
 *
 */
public class NutritionFacts {
    public static class Builder {

        // optional parameters;
        private int calories;
        private int carbohydrate;

        private int fact;
        private final int servings;
        // required parameters;
        private final int servingSize;
        private int sodium;

        public Builder(int servingSize, int servings) {
            this.servings = servings;
            this.servingSize = servingSize;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }

        public Builder calorise(int val) {
            calories = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public Builder fact(int val) {
            fact = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }
    }

    private final int calories;

    private final int carbohydrate;

    private final int fact;

    private final int servings;

    private final int servingSize;

    private final int sodium;

    private NutritionFacts(Builder builder) {
        servings = builder.servings;
        servingSize = builder.servingSize;
        calories = builder.calories;
        fact = builder.fact;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getFact() {
        return fact;
    }

    public int getServings() {
        return servings;
    }

    public int getServingSize() {
        return servingSize;
    }

    public int getSodium() {
        return sodium;
    }

    @Override
    public String toString() {
        return String
                .format("memeber as serving: %d, servingSize: %d, fact: %d, calories: %d",
                        servings, servingSize, fact, calories);
    }
}
