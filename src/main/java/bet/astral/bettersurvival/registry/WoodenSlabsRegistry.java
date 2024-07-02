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

public class WoodenSlabsRegistry implements Registry<Material> {
	public static WoodenSlabsRegistry REGISTRY = new WoodenSlabsRegistry();
	private final Map<NamespacedKey, Material> materialMap = new HashMap<>();

	public static Material OAK_SLAB = Material.OAK_SLAB;
	public static Material SPRUCE_SLAB = Material.SPRUCE_SLAB;
	public static Material BIRCH_SLAB  = Material.BIRCH_SLAB;
	public static Material JUNGLE_SLAB = Material.JUNGLE_SLAB;
	public static Material ACACIA_SLAB = Material.ACACIA_SLAB;
	public static Material CHERRY_SLAB = Material.CHERRY_SLAB;
	public static Material DARK_OAK_SLAB = Material.DARK_OAK_SLAB;
	public static Material MANGROVE_SLAB = Material.MANGROVE_SLAB;
	public static Material BAMBOO_SLAB = Material.BAMBOO_SLAB;
	public static Material CRIMSON_SLAB = Material.CRIMSON_SLAB;
	public static Material WARPED_SLAB = Material.WARPED_SLAB;
	public static Material BAMBOO_MOSAIC = Material.BAMBOO_SLAB;

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
