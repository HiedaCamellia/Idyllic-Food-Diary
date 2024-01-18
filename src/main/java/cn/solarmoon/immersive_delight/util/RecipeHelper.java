package cn.solarmoon.immersive_delight.util;

import cn.solarmoon.immersive_delight.common.IMRecipes;
import cn.solarmoon.immersive_delight.common.recipes.*;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

public class RecipeHelper {

    public static class GetRecipes {

        /**
         * 获取擀面杖所有配方
         */
        public static List<RollingPinRecipe> rollingRecipes(Level level) {
            List<RollingPinRecipe> allRecipes;
            //动态遍历擀面杖配方
            RecipeManager recipeManager = level.getRecipeManager();
            allRecipes = recipeManager.getAllRecipesFor(IMRecipes.ROLLING_RECIPE.get()).stream()
                    .filter(Objects::nonNull)
                    .toList();
            return allRecipes;
        }

        /**
         * 获取烧水壶所有配方
         */
        public static List<KettleRecipe> kettleRecipes(Level level) {
            List<KettleRecipe> allRecipes;
            RecipeManager recipeManager = level.getRecipeManager();
            allRecipes = recipeManager.getAllRecipesFor(IMRecipes.KETTLE_RECIPE.get()).stream()
                    .filter(Objects::nonNull)
                    .toList();
            return allRecipes;
        }

        /**
         * 获取所有泡茶配方
         */
        public static List<CupRecipe> CupRecipes(Level level) {
            List<CupRecipe> allRecipes;
            RecipeManager recipeManager = level.getRecipeManager();
            allRecipes = recipeManager.getAllRecipesFor(IMRecipes.CUP_RECIPE.get()).stream()
                    .filter(Objects::nonNull)
                    .toList();
            return allRecipes;
        }

        /**
         * 获取所有菜刀配方
         */
        public static List<CleaverRecipe> CleaverRecipe(Level level) {
            List<CleaverRecipe> allRecipes;
            RecipeManager recipeManager = level.getRecipeManager();
            allRecipes = recipeManager.getAllRecipesFor(IMRecipes.CLEAVER_RECIPE.get()).stream()
                    .filter(Objects::nonNull)
                    .toList();
            return allRecipes;
        }

        /**
         * 获取所有汤锅配方
         */
        public static List<SoupPotRecipe> SoupPotRecipe(Level level) {
            List<SoupPotRecipe> allRecipes;
            RecipeManager recipeManager = level.getRecipeManager();
            allRecipes = recipeManager.getAllRecipesFor(IMRecipes.SOUP_POT_RECIPE.get()).stream()
                    .filter(Objects::nonNull)
                    .toList();
            return allRecipes;
        }

    }

    public record ChanceResult(ItemStack stack, float chance) {

        public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

        public static final ChanceResult EMPTY = new ChanceResult(ItemStack.EMPTY, 1);

        public ItemStack rollOutput(RandomSource rand, int fortuneLevel) {
            int outputAmount = stack.getCount();

            for (int roll = 0; roll < stack.getCount(); ++roll) {
                if ((double) rand.nextFloat() > (double) chance + (double) fortuneLevel * 0.1) {
                    --outputAmount;
                }
            }

            if (outputAmount == 0) {
                return ItemStack.EMPTY;
            } else {
                ItemStack out = stack.copy();
                out.setCount(outputAmount);
                return out;
            }
        }

        public JsonElement serialize() {
            JsonObject json = new JsonObject();
            ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
            json.addProperty("item", resourceLocation.toString());
            int count = stack.getCount();
            if (count != 1) {
                json.addProperty("count", count);
            }

            if (stack.hasTag()) {
                json.add("nbt", JsonParser.parseString(stack.getTag().toString()));
            }

            if (chance != 1.0F) {
                json.addProperty("chance", this.chance);
            }

            return json;
        }

        public static ChanceResult deserialize(JsonElement je) {
            if (!je.isJsonObject()) {
                throw new JsonSyntaxException("Must be a json object");
            } else {
                JsonObject json = je.getAsJsonObject();
                String itemId = GsonHelper.getAsString(json, "item");
                int count = GsonHelper.getAsInt(json, "count", 1);
                float chance = GsonHelper.getAsFloat(json, "chance", 1.0F);
                ItemStack itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), count);
                if (GsonHelper.isValidPrimitive(json, "nbt")) {
                    try {
                        JsonElement element = json.get("nbt");
                        itemstack.setTag(TagParser.parseTag(element.isJsonObject() ? GSON.toJson(element) : GsonHelper.convertToString(element, "nbt")));
                    } catch (CommandSyntaxException var7) {
                        var7.printStackTrace();
                    }
                }

                return new ChanceResult(itemstack, chance);
            }
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeItem(stack());
            buf.writeFloat(chance());
        }

        public static ChanceResult read(FriendlyByteBuf buf) {
            return new ChanceResult(buf.readItem(), buf.readFloat());
        }

    }
}