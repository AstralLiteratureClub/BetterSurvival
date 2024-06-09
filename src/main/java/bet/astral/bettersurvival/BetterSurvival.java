package bet.astral.bettersurvival;

import bet.astral.bettersurvival.commands.CommandDay;
import bet.astral.bettersurvival.commands.CommandHat;
import bet.astral.bettersurvival.format.AllItemDisplayListener;
import bet.astral.bettersurvival.format.ChatFormatListener;
import bet.astral.bettersurvival.format.ConnectionFormatListener;
import bet.astral.bettersurvival.format.ItemDisplayListener;
import bet.astral.bettersurvival.format.LinkFixListener;
import bet.astral.bettersurvival.gameplay.listeners.player.ColoredAnvilNameListener;
import bet.astral.bettersurvival.gameplay.listeners.player.DeathLocationListener;
import bet.astral.bettersurvival.gameplay.listeners.player.RecipeListeners;
import bet.astral.bettersurvival.gameplay.listeners.world.ArmorStandSwitchTypeListener;
import bet.astral.bettersurvival.gameplay.listeners.world.ClickThroughItemFramesListeners;
import bet.astral.bettersurvival.gameplay.listeners.world.PlayerHeadDropsListener;
import bet.astral.bettersurvival.gameplay.listeners.world.SleepSkipListener;
import bet.astral.bettersurvival.gameplay.listeners.world.SoulSandValleySkeletonHorseSpawnListener;
import bet.astral.bettersurvival.gameplay.listeners.world.SpawnBabyOnDeathListener;
import bet.astral.bettersurvival.gameplay.recipes.InvisibleGlowItemFrameCraftingRecipe;
import bet.astral.bettersurvival.gameplay.recipes.InvisibleItemFrameCraftingRecipe;
import bet.astral.bettersurvival.gameplay.recipes.Recipe;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class BetterSurvival extends JavaPlugin {
    public MiniMessage miniMessage = MiniMessage.miniMessage();
    public final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    public final List<String> joins = new LinkedList<>();
    public final List<String> quits = new LinkedList<>();
    public final Set<String> links = new HashSet<>();
    public final Map<Integer, Integer> sleepRequirements = new HashMap<>();
    public final Set<Recipe> recipes = new HashSet<>();
    public String chatFormat;
    public final Random random = new Random();
    public String itemFormat;
    public String itemFormatEmpty;

	@Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration configuration = getConfig();
        if (!configuration.isSet("always-replace-config") || configuration.getBoolean("always-replace-config", false)){
            File file = new File(getDataFolder(), "config.yml");
            file.delete();
            saveDefaultConfig();
            reloadConfig();
            configuration = getConfig();
        }
        links.addAll(configuration.getStringList("links"));
        joins.addAll(configuration.getStringList("join-messages"));
        quits.addAll(configuration.getStringList("quit-messages"));
        chatFormat = configuration.getString("chat-format");
        itemFormat = configuration.getString("chat-item-format");
        itemFormatEmpty = configuration.getString("chat-item-air-format");
        int highest = 1;
        for (int i = 0; i < 20; i++){
            if (!configuration.isInt("sleep."+i)){
                sleepRequirements.put(i, highest);
                continue;
            }
            int req = configuration.getInt("sleep."+i);
            if (req > highest){
                highest = req;
            }
            sleepRequirements.put(i, req);
        }

        register(new ChatFormatListener(this));
        register(new ConnectionFormatListener(this));
        register(new ItemDisplayListener(this));
        register(new AllItemDisplayListener(this));
        register(new LinkFixListener(this));

        register(new ColoredAnvilNameListener(this));
        register(new DeathLocationListener(this));
        register(new SleepSkipListener(this));
        register(new PlayerHeadDropsListener(this));
        register(new RecipeListeners(this));
        register(new ClickThroughItemFramesListeners(this));
        register(new SoulSandValleySkeletonHorseSpawnListener(this));
        register(new SpawnBabyOnDeathListener(this));
        register(new ArmorStandSwitchTypeListener(this));
        register(new ArmorStandSwitchTypeListener(this));

        recipe(new InvisibleItemFrameCraftingRecipe(this).register());
        recipe(new InvisibleGlowItemFrameCraftingRecipe(this).register());

        new CommandHat().register(this);
        new CommandDay().register(this);
        getLogger().info("Enabled better survival");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled better survival");
    }

    public void register(@NotNull Listener listener){
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void recipe(@NotNull Recipe recipe){
        recipes.add(recipe);
        if (recipe instanceof Listener listener){
            register(listener);
        }
    }

    @NotNull
    public NamespacedKey key(@NotNull String value){
        return new NamespacedKey(this, value);
    }
}