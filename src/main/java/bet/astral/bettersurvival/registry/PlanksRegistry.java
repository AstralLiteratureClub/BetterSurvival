package bet.astral.bettersurvival.registry;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class PlanksRegistry implements Registry<Material> {
	public static PlanksRegistry REGISTRY = new PlanksRegistry();
	private final Map<NamespacedKey, Material> materialMap = new HashMap<>();
	@bet.astral.bettersurvival.registry.Registry
	public static Material OAK_PLANKS = Material.OAK_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material SPRUCE_PLANKS = Material.SPRUCE_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material BIRCH_PLANKS  = Material.BIRCH_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material JUNGLE_PLANKS = Material.JUNGLE_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material ACACIA_PLANKS = Material.ACACIA_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material CHERRY_PLANKS = Material.CHERRY_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material DARK_OAK_PLANKS = Material.DARK_OAK_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material MANGROVE_PLANKS = Material.MANGROVE_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material BAMBOO_PLANKS = Material.BAMBOO_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material CRIMSON_PLANKS = Material.CRIMSON_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material WARPED_PLANKS = Material.WARPED_PLANKS;
	@bet.astral.bettersurvival.registry.Registry
	public static Material BAMBOO_MOSAIC = Material.BAMBOO_MOSAIC;

	static {
		for (Field field : PlanksRegistry.class.getFields()){
			if (field.isAnnotationPresent(bet.astral.bettersurvival.registry.Registry.class)){
				try {
					Material material = (Material) field.get(null);
					REGISTRY.materialMap.put(material.getKey(), material);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public @Nullable Material get(@NotNull NamespacedKey namespacedKey) {
		return materialMap.get(namespacedKey);
	}

	@Override
	public @NotNull Stream<Material> stream() {
		return materialMap.values().stream();
	}

	@NotNull
	@Override
	public Iterator<Material> iterator() {
		return materialMap.values().iterator();
	}
}
