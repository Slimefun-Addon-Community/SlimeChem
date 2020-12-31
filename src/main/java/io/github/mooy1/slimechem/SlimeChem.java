package io.github.mooy1.slimechem;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.IsotopeLoader;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.slimechem.setup.Registry;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;


public class SlimeChem extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static SlimeChem instance;
    
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setPlugin(this);
        PluginUtils.setupConfig();
        MessageUtils.setPrefix(ChatColors.color("&7[&bSlimeChem&7]&f "));
        
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 9490);
        
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/SlimeChem/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/

        IsotopeLoader isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();

        Registry.setup(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/SlimeChem/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
