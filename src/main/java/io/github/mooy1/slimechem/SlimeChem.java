package io.github.mooy1.slimechem;

import io.github.mooy1.slimechem.setup.Registry;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import javax.annotation.Nonnull;

public class SlimeChem extends JavaPlugin implements SlimefunAddon {
    
    @Getter
    private static SlimeChem instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        updateConfig();
        
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 9490);
        
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/SlimeChem/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/
        
        Registry.setup(this);
    }
    
    private void updateConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
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
